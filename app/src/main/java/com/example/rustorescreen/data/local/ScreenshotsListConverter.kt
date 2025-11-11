package com.example.rustorescreen.data.local

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json

class ScreenshotsListConverter {
    private val json = Json { ignoreUnknownKeys = true }
    @TypeConverter
    fun fromList(list: List<String>): String {
        return json.encodeToString(list)
    }

    @TypeConverter
    fun toList(data: String?): List<String> {
        if (data.isNullOrBlank()) {
            return emptyList<String>()
        }
        return json.decodeFromString(data)
    }
}