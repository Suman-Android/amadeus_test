package com.coforge.amadeus.models

data class Main(
    val humidity: Int=0,
    val pressure: Double=0.0,
    val temp: Double=0.0,
    val temp_max: Double=0.0,
    val temp_min: Double=0.0,
    val sea_level: Double=0.0,
    val grnd_level: Double=0.0
)
