package com.coforge.amadeus

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.coforge.amadeus.adapter.WeatherPagingAdapter
import com.coforge.amadeus.common.footer.FooterAdapter
import com.coforge.amadeus.databinding.ActivityMainBinding
import com.coforge.amadeus.models.WeatherItemUiState
import com.coforge.amadeus.models.WeatherUiState
import com.coforge.amadeus.utils.collect
import com.coforge.amadeus.utils.collectLast
import com.coforge.amadeus.utils.executeWithAction
import com.coforge.amadeus.utils.hideKeyboard
import com.google.android.material.internal.ViewUtils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.map
import java.io.InputStream
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var mainViewModel: MainViewModel

    @Inject
    lateinit var weatherPagingAdapter: WeatherPagingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        readDataFromFile()
        setAdapter()
        setListener()
        collectLast(mainViewModel.weatherItemsUiStates, ::setUsers)
    }

    /*
        Get "weather_14 File from asset
    */
    private fun readDataFromFile() {
        val inputStream: InputStream = assets.open("weather_14.json")
        mainViewModel.readDataFromFile(inputStream)
    }

    private fun setListener() {
        binding.btnRetry.setOnClickListener { weatherPagingAdapter.retry() }
    }

    private fun setAdapter() {
        collect(flow = weatherPagingAdapter.loadStateFlow
            .distinctUntilChangedBy { it.source.refresh }
            .map { it.refresh },
            action = ::setUsersUiState
        )
        binding.rvWeatherList.adapter =
            weatherPagingAdapter.withLoadStateFooter(FooterAdapter(weatherPagingAdapter::retry))
    }

    private fun setUsersUiState(loadState: LoadState) {
        binding.executeWithAction {
            usersUiState = WeatherUiState(loadState)
        }
    }

    private suspend fun setUsers(userItemsPagingData: PagingData<WeatherItemUiState>) {
        weatherPagingAdapter.submitData(userItemsPagingData)
    }


    //option menu has search bar
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