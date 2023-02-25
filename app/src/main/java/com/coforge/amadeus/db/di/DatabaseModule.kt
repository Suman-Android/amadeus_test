package com.coforge.amadeus.db.di

import android.content.Context
import androidx.room.Room
import com.amadeus.myapplication.db.Converters
import com.amadeus.myapplication.db.WeatherForecastDao
import com.amadeus.myapplication.db.WeatherForecastDatabase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    fun provideLogDao(database: WeatherForecastDatabase): WeatherForecastDao {
        return database.weatherDao()
    }

    @Provides
    @Singleton
    fun provideWeatherDatabase(@ApplicationContext appContext: Context): WeatherForecastDatabase {
        return Room.databaseBuilder(
            appContext,
            WeatherForecastDatabase::class.java,
            "weather_database.db"
        ).addTypeConverter(Converters()).build()
    }
}