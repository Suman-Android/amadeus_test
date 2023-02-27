package com.coforge.amadeus.utils

import java.text.SimpleDateFormat
import java.util.*

object Utility {
    private const val DATE_FORMAT_DD_MMM_YYYY_HH_MM = "dd MMM yyyy HH:mm"

    fun convertLongToTime(time: Long?): String {
        if (time != null) {
            val date = Date(time)
            val format = SimpleDateFormat(DATE_FORMAT_DD_MMM_YYYY_HH_MM)
            return format.format(date)
        } else {
            return ""
        }
    }
}