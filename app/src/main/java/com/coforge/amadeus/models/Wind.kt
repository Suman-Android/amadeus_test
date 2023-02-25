package com.coforge.amadeus.models


data class Wind(
    val deg: Double=0.0,
    val speed: Double=0.0,
    val var_beg: Int=0,
    val var_end: Int=0,
    val gust: Double=0.0

)