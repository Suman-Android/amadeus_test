package com.coforge.amadeus.db.entites

import androidx.room.Embedded

data class City(
    @Embedded
    val coord: Coord,
    val country: String = "",
    val findname: String = "",
    val id: Int = 0,
    val langs: List<Lang>?,
    val name: String = "",
    val zoom: Int = 0
)