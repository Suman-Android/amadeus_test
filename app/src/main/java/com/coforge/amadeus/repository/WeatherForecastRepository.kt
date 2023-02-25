package com.coforge.amadeus.repository

import androidx.paging.PagingSource
import com.amadeus.myapplication.db.WeatherForecastDao
import com.coforge.amadeus.db.entites.WeatherDataItem
import javax.inject.Inject


class WeatherForecastRepository @Inject constructor(private val weatherForecastDao: WeatherForecastDao) {

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


}