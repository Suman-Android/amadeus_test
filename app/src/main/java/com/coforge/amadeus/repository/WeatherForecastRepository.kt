package com.coforge.amadeus.repository

import androidx.paging.PagingSource
import com.amadeus.myapplication.db.WeatherForecastDao
import com.coforge.amadeus.common.base.BaseApi
import com.coforge.amadeus.db.entites.WeatherDataItem
import com.coforge.amadeus.network.ApiService
import com.coforge.amadeus.network.ResultWrapper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class WeatherForecastRepository @Inject constructor(
    private val weatherForecastDao: WeatherForecastDao,
    val apiService: ApiService
) : BaseApi() {

    /*
    *  Query  data from weather table
    *  Returns all rows from table
    */
    fun getWeatherList(): PagingSource<Int, WeatherDataItem> {
        return weatherForecastDao.getAllWeather()
    }

    //inserting data into db
    suspend fun saveWeatherDataItem(weatherDataItem: WeatherDataItem) {
        weatherForecastDao.insertWeather(weatherDataItem)
    }

    //return total no. of rows
    fun getCount(): Int {
        return weatherForecastDao.getCount()
    }

    /*
    *  Query  city from weather table
    *  Returns all the related cities rows from table
    */
    fun getCitySearchResult(query: String): PagingSource<Int, WeatherDataItem> {
        return weatherForecastDao.searchCity(query)
    }


    //We wrap the actual api call into safe api which is the base class for
    // all the API
    suspend fun callWeatherApi(): Flow<ResultWrapper<WeatherDataItem>> =
        safeApiCall {
            apiService.getWeather()
        }

}