package com.coforge.amadeus.utils

import java.text.SimpleDateFormat
import java.util.*

object Utility {

    fun convertLongToTime(time: Long?): String {
        if (time != null) {
            val date = Date(time)
            val format = SimpleDateFormat("dd MMM yyyy HH:mm")
            return format.format(date)
        } else {
            return ""
        }
    }
}