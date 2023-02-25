package com.coforge.amadeus

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coforge.amadeus.models.WeatherDataItem
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.InputStream

class MainViewModel : ViewModel() {


    /*
    *Read from input stream line by line
    */
    fun readDataFromWeatherStream(inputStream: InputStream) {
        viewModelScope.launch(Dispatchers.IO) {
            val gson = Gson()
            val weatherList = mutableListOf<WeatherDataItem>()
            var weatherDataItem: WeatherDataItem
            inputStream.bufferedReader().forEachLine {
                    Log.e("List ${weatherList.size}", it.toString())
                }
            }
        }

}