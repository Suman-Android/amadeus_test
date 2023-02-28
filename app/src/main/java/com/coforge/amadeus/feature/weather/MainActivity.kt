package com.coforge.amadeus.feature.weather

import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.coforge.amadeus.R
import com.coforge.amadeus.adapter.WeatherPagingAdapter
import com.coforge.amadeus.common.footer.FooterAdapter
import com.coforge.amadeus.databinding.ActivityMainBinding
import com.coforge.amadeus.db.entites.WeatherItemUiState
import com.coforge.amadeus.db.entites.WeatherUiState
import com.coforge.amadeus.utils.collect
import com.coforge.amadeus.utils.executeWithAction
import com.coforge.amadeus.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.io.InputStream
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val mainViewModel: MainViewModel by viewModels()

    @Inject
    lateinit var weatherPagingAdapter: WeatherPagingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        readDataFromFile()
        setAdapter()
        setListener()
    }

    //Load "weather_14 File from asset
    private fun readDataFromFile() {
        val inputStream: InputStream = assets.open("weather_14.json")
        mainViewModel.readDataFromFile(inputStream)
    }

    //Set button ClickListener
    private fun setListener() {
        binding.btnRetry.setOnClickListener { weatherPagingAdapter.retry() }
    }


    /*
    * Stream is collected by using collect
    * For making flow lifecycle aware, collecting streams into Lifecycle Started scope
    * When the stream is collected then we are setting the adapter
    * */
    private fun setAdapter() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                collect(mainViewModel.weatherItemsUiStates, ::setUsers)
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                collect(flow = weatherPagingAdapter.loadStateFlow
                    .distinctUntilChangedBy { it.source.refresh }
                    .map { it.refresh },
                    action = ::setUsersUiState
                )
            }
        }

        binding.rvWeatherList.adapter =
            weatherPagingAdapter.withLoadStateFooter(FooterAdapter(weatherPagingAdapter::retry))
    }


    //binding data
    private fun setUsersUiState(loadState: LoadState) {
        binding.executeWithAction {
            usersUiState = WeatherUiState(loadState)
        }
    }

    //setting adapter
    private suspend fun setUsers(userItemsPagingData: PagingData<WeatherItemUiState>) {
        weatherPagingAdapter.submitData(userItemsPagingData)
    }


    /*
    * OptionMenu has search capability
    * When user enters text(city name) in searchbar
    * then it communicates/query with viewmodel for result Data
    * When searchbar is blank then it hides the soft keyboard
    * */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_weather, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null)
                    mainViewModel.onSubmitQuery(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    mainViewModel.onSubmitQuery(newText)
                    if (newText.isEmpty())
                        searchView.hideKeyboard()
                }
                return true
            }
        })

        return true
    }


}