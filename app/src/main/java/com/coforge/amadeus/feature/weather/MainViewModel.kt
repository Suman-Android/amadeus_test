package com.coforge.amadeus.feature.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.coforge.amadeus.db.entites.WeatherDataItem
import com.coforge.amadeus.db.entites.WeatherItemUiState
import com.coforge.amadeus.network.doOnFailure
import com.coforge.amadeus.network.doOnLoading
import com.coforge.amadeus.network.doOnSuccess
import com.coforge.amadeus.repository.WeatherForecastRepository
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val weatherRepository: WeatherForecastRepository
) : ViewModel() {
    var currentQuery = ""

    /*
    * Getting the result based on query text
    * If query text is empty then we reload all the default data
    * else we loading data based on query(city) text
    * */
    private var pagingSource: PagingSource<Int, WeatherDataItem>? = null
        get() {
            if (field == null || field?.invalid == true) {
                if (currentQuery.isEmpty())
                    field = weatherRepository.getWeatherList()
                else
                    field = weatherRepository.getCitySearchResult(currentQuery)
            }
            return field
        }


    /*
    * Sets page config
    * Convert Paging Source data into flow
    * Map flow into WeatherItemUiState
    * */
    val weatherItemsUiStates = Pager(
        PagingConfig(
            pageSize = PAGE_SIZE,
            prefetchDistance = PREFETCH_DISTANCE,
            enablePlaceholders = ENABLE_PLACE_HOLDER,
            maxSize = PAGE_SIZE + 2 * PREFETCH_DISTANCE
        )
    ) {
        pagingSource!!
    }.flow.map { pagingData ->
        pagingData.map { userModel -> WeatherItemUiState(userModel) }
    }.cachedIn(viewModelScope)


    //For saving Data into db it will communicate with repository
    fun saveWeatherData(weatherDataItem: WeatherDataItem) {
        viewModelScope.launch {
            weatherRepository.saveWeatherDataItem(weatherDataItem)
        }
    }

    /*
    * Launching coroutine and perform operations on IO Thread
    * Reading Input Stream Line By Line
    * Converting each line into WeatherDataItem java object
    */
    fun readDataFromFile(inputStream: InputStream) {
        var count: Int
        viewModelScope.launch(Dispatchers.IO) {
            count = weatherRepository.getCount()
            if (count == 0) {
                val gson = Gson()
                var weatherDataItem: WeatherDataItem
                inputStream.bufferedReader().forEachLine {
                    weatherDataItem = gson.fromJson(it, WeatherDataItem::class.java)
                    saveWeatherData(weatherDataItem)
                }
            }
        }
    }


    // If pageSource is not null the invalidates the pageSource
    fun onSubmitQuery(query: String) {
        currentQuery = query
        pagingSource?.invalidate()
    }

    /*
    * API Calling Sample
    * It communicate with repository for actual api call
    * As soon api called flow is collecting the result
    * */
    fun callWeatherApi() = viewModelScope.launch {
        weatherRepository.callWeatherApi()
            .doOnLoading {
                TODO("Show ProgressBar")
            }
            .doOnSuccess { data ->
                TODO("Hide ProgressBar & Show data in the UI")
            }
            .doOnFailure { error ->
                TODO("Show error msg to user")
            }
            .collect()
    }

    companion object {
        private const val PAGE_SIZE = 50
        private const val PREFETCH_DISTANCE = 150
        private const val ENABLE_PLACE_HOLDER = false

    }

}