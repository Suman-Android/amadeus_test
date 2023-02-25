package com.coforge.amadeus.feature.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.coforge.amadeus.db.entites.WeatherDataItem
import com.coforge.amadeus.db.entites.WeatherItemUiState
import com.coforge.amadeus.repository.WeatherForecastRepository
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
    *Getting the result based on query text
    *If query text is empty then we reload all the default data
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


    val weatherItemsUiStates = Pager(
        PagingConfig(
            pageSize = 20,
            enablePlaceholders = false,
            maxSize = 200
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
    *Reading Input Stream Line By Line
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


    //if pageSource is not null the invalidates the pageSource
    fun onSubmitQuery(query: String) {
        currentQuery = query
        pagingSource?.invalidate()
    }

}