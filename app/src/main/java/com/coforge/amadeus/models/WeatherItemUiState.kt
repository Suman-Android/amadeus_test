package com.coforge.amadeus.models

import android.view.View.GONE
import android.view.View.VISIBLE
import com.coforge.amadeus.common.BaseUiState
import com.coforge.amadeus.utils.Utility

data class WeatherItemUiState(private val weatherDataItem: WeatherDataItem) : BaseUiState() {

    private fun getCityName() = weatherDataItem.city?.name

    fun getCityCountryName() = getCityName() + ", " + getCountryName()

    fun getCityID() = weatherDataItem.city?.id

    private fun getTime() = weatherDataItem.time

    fun getUpdatedAt() = "Updated at ${Utility.convertLongToTime(getTime().toLong())}"

    private fun getCountryName() = weatherDataItem.city?.country

    fun getMain() = weatherDataItem.weather?.get(0)?.main

    fun getStatus() = weatherDataItem.main?.temp

    fun getTemperature() = weatherDataItem.main?.temp.toString()

    fun getMinTemp() = "Min Temp : ${weatherDataItem.main?.temp_min}"

    fun getMaxTemp() = "Max Temp : ${weatherDataItem.main?.temp_max}"

    fun getPressure() = "Pressure : ${weatherDataItem.main?.pressure}"

    fun getHumidity() = "Humidity : ${weatherDataItem.main?.pressure}"

    fun getWind() =
        "Wind : ${weatherDataItem.wind?.deg}°  Speed : ${weatherDataItem.wind?.speed}km/h"

    fun geCloud() = "Clouds : ${weatherDataItem.clouds?.all}"

    fun getRain() = "Rain : ${weatherDataItem.rain?.threeHour}hours"

    fun getWeather() =
        "${weatherDataItem.weather?.get(0)?.main.toString()} : ${weatherDataItem.weather?.get(0)?.description.toString()}"

    fun getRainVisibility(): Int {
        return if (weatherDataItem.rain != null) VISIBLE else GONE
    }
}