package com.coforge.amadeus.db.entites

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.amadeus.myapplication.models.Clouds

@Entity(tableName = "weather_table")
data class WeatherDataItem(
    @PrimaryKey(autoGenerate = true)
    val pageNo: Int = 0,
    val time: Int = 0,
    val weather: List<Weather>?,

    @Embedded
    val city: City?,
    @Embedded
    val clouds: Clouds?,
    @Embedded
    val main: Main?,
    @Embedded
    val rain: Rain?,
    @Embedded
    val wind: Wind?
)