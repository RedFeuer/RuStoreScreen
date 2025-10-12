package com.example.rustorescreen.model

import androidx.compose.ui.graphics.vector.ImageVector

data class AppItem(
    val id: Int,
    val title: String,
    val subtitle: String,
    val category: String,
    val icon: ImageVector
)