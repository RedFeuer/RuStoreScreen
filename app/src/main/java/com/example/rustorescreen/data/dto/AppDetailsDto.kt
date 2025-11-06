package com.example.rustorescreen.data.dto

import com.example.rustorescreen.domain.domainModel.AppCategory
import kotlinx.serialization.Serializable


@Serializable
data class AppDetailsDto(
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
