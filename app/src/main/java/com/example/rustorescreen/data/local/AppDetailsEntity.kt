package com.example.rustorescreen.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.rustorescreen.domain.domainModel.AppCategory
import com.example.rustorescreen.domain.domainModel.InstallStatus

@Entity(tableName = "app_details")
data class AppDetailsEntity (
    @PrimaryKey
    val id: String,
    val title: String,
    val installStatus: InstallStatus, // по умолчанию - не установлено
    val developer: String,
    val category: AppCategory,
    val ageRating: Int,
    val size: Float,
    val iconUrl: String,
    val screenshotUrlList: List<String> = emptyList<String>(),
    val description: String,
)