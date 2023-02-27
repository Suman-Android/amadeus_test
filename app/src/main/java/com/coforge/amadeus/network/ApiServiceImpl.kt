package com.coforge.amadeus.network

import com.coforge.amadeus.db.entites.WeatherDataItem
import retrofit2.Response
import javax.inject.Inject

class ApiServiceImpl @Inject constructor() : ApiService {
    override suspend fun getWeather(): Response<WeatherDataItem> {
        TODO("Not yet implemented")
    }
}