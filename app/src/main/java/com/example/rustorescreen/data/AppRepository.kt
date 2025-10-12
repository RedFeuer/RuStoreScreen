package com.example.rustorescreen.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.LocationOn
import com.example.rustorescreen.model.AppItem

object AppRepository {
    private val items = listOf(
        AppItem(
            id = 1,
            title = "Сбербанк Онлайн",
            subtitle = "Больше чем банк",
            category = "Финансы",
            icon = Icons.Outlined.PlayArrow
        ),
        AppItem(
            id = 2,
            title = "Яндекс Браузер",
            subtitle = "Быстрые и безопасный браузер",
            category = "Инструменты",
            icon = Icons.Outlined.Build
        ),
        AppItem(
            id = 3,
            title = "Почта Mail.ru",
            subtitle = "Почтовый клиент для любых ящиков",
            category = "Инструменты",
            icon = Icons.Outlined.Build
        ),
        AppItem(
            id = 4,
            title = "Яндекс Навигатор",
            subtitle = "Парковки и заправки - по пути",
            category = "Транспорт",
            icon = Icons.Outlined.LocationOn
        ),
        AppItem(
            id = 5,
            title = "Мой МТС",
            subtitle = "Мой МТС - центр экосистемы МТС",
            category = "Инструменты",
            icon = Icons.Outlined.Build
        ),
        AppItem(
            id = 6,
            title = "Яндекс с Алисой",
            subtitle = "Яндекс - всегда под рукой",
            category = "Инструменты",
            icon = Icons.Outlined.Build
        ),
    )

    fun all(): List<AppItem> = items
    fun byId(id: Int): AppItem? = items.find { it.id == id }
}