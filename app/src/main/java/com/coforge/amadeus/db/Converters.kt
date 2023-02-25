package com.amadeus.myapplication.db

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.coforge.amadeus.db.entites.Lang
import com.coforge.amadeus.db.entites.Weather
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


@ProvidedTypeConverter
class Converters {
    @TypeConverter // note this annotation
    fun fromWeatherList(weather: List<Weather?>?): String? {
        if (weather == null) {
            return null
        }
        val gson = Gson()
        val type =
            object : TypeToken<List<Weather?>?>() {}.type
        return gson.toJson(weather, type)
    }

    @TypeConverter // note this annotation
    fun toWeatherList(optionValuesString: String?): List<Weather>? {
        if (optionValuesString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Weather?>?>() {}.type
        return gson.fromJson<List<Weather>>(optionValuesString, type)
    }

    @TypeConverter // note this annotation
    fun fromLangList(lang: List<Lang?>?): String? {
        if (lang == null) {
            return null
        }
        val gson = Gson()
        val type =
            object : TypeToken<List<Lang?>?>() {}.type
        return gson.toJson(lang, type)
    }

    @TypeConverter // note this annotation
    fun toLangList(optionValuesString: String?): List<Lang>? {
        if (optionValuesString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Lang?>?>() {}.type
        return gson.fromJson<List<Lang>>(optionValuesString, type)
    }
}
