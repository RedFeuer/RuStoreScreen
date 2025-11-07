package com.example.rustorescreen.data.dto

import com.example.rustorescreen.domain.domainModel.AppCategory
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class AppDetailsDto(

    val id: String = "",

    @SerialName("name")
    val title: String = "",

    val developer: String = "",
    val category: AppCategory = AppCategory.APP,
    val ageRating: Int = 0,
    val size: Float = 0.0.toFloat(),
    val iconUrl: String = "",
    val screenshotUrlList: List<String> = emptyList<String>(),
    val description: String = "",
)
