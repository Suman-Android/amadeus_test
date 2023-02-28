package com.amadeus.myapplication.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.coforge.amadeus.db.entites.WeatherDataItem

@Database(entities = [WeatherDataItem::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
@SuppressWarnings
abstract class WeatherForecastDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherForecastDao
}