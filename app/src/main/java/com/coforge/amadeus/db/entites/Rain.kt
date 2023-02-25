package com.coforge.amadeus.db.entites

import com.google.gson.annotations.SerializedName

data class Rain(
    @field:SerializedName("3h") val threeHour: Double = 0.0,
    @field:SerializedName("1h") val oneHour: Double = 0.0

)