package com.example.rustorescreen.data.local

import androidx.room.TypeConverter
import org.json.JSONArray

class ScreenshotsListConverter {
    @TypeConverter
    fun fromList(list: List<String>?): String? {
        if (list == null) {
            return null
        }
        val json = JSONArray()
        list.forEach{ it ->
            json.put(it)
        }
        return json.toString()
    }

    @TypeConverter
    fun toList(data: String?): List<String> {
        if (data.isNullOrEmpty()) {
            return emptyList()
        }
        val json = JSONArray(data)
        val result = MutableList<String>(json.length()) { "" }
        for (i in 0 until json.length()) result[i] = json.optString(i)
        return result;
    }
}