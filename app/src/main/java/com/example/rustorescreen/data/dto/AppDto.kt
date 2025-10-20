package com.example.rustorescreen.data.dto

import com.example.rustorescreen.domain.domainModel.AppCategory

data class AppDto(
    val id: Int,
    val title: String,
    val developer: String,
    val category: AppCategory,
    val ageRating: Int,
    val size: Float,
    val iconUrl: String,
    val screenshotUrlList: List<String>,
    val description: String,
)
