package com.coforge.amadeus

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.coforge.amadeus.models.WeatherDataItem
import com.coforge.amadeus.models.WeatherItemUiState
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

    fun saveWeatherData(weatherDataItem: WeatherDataItem) {
        viewModelScope.launch {
            weatherRepository.saveWeatherDataItem(weatherDataItem)
        }
    }

    fun readDataFromFile(inputStream: InputStream) {
        viewModelScope.launch(Dispatchers.IO) {
                val gson = Gson()
                var weatherDataItem: WeatherDataItem
                inputStream.bufferedReader().forEachLine {
                    weatherDataItem = gson.fromJson(it, WeatherDataItem::class.java)
                    saveWeatherData(weatherDataItem)
                }
        }
    }


}