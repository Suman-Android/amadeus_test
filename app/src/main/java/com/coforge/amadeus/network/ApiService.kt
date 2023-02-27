package com.coforge.amadeus.network

import com.coforge.amadeus.db.entites.WeatherDataItem
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("sample/weather_14.json.gz")
    suspend fun getWeather(): Response<WeatherDataItem>
}