package com.example.rustorescreen.domain.domainModel

import kotlinx.serialization.SerialName

enum class AppCategory {
    @SerialName("Приложения")
    APP,

    @SerialName("Игры")
    GAME,

    @SerialName("Производительность")
    PRODUCTIVITY,

    @SerialName("Социальные сети")
    SOCIAL,

    @SerialName("Образование")
    EDUCATION,

    @SerialName("Развлечения")
    ENTERTAINMENT,

    @SerialName("Музыка")
    MUSIC,

    @SerialName("Видео")
    VIDEO,

    @SerialName("Фотография")
    PHOTOGRAPHY,

    @SerialName("Здоровье")
    HEALTH,

    @SerialName("Спорт")
    SPORTS,

    @SerialName("Новости")
    NEWS,

    @SerialName("Книги")
    BOOKS,

    @SerialName("Бизнес")
    BUSINESS,

    @SerialName("Финансы")
    FINANCE,

    @SerialName("Путешествия")
    TRAVEL,

    @SerialName("Карты")
    MAPS,

    @SerialName("Еда")
    FOOD,

    @SerialName("Покупки")
    SHOPPING,

    @SerialName("Утилиты")
    UTILITIES
}