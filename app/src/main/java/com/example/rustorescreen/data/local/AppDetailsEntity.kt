package com.example.rustorescreen.data.local

import androidx.room.Entity
import com.example.rustorescreen.domain.domainModel.AppCategory

@Entity
data class AppDetailsEntity (
    val id: String,
    val title: String,
    val developer: String,
    val category: AppCategory,
    val ageRating: Int,
    val size: Float,
    val iconUrl: String,
    val screenshotUrlList: List<String>,
    val description: String,
)