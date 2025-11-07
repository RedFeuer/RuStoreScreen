package com.example.rustorescreen.domain.domainModel

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class AppCategory {
    @SerialName("Приложения")
    APP,

    @SerialName("Игры")
    GAME,

    @SerialName("Производительность")
    PRODUCTIVITY,

    @SerialName("Общение")
    SOCIAL,

    @SerialName("Образование")
    EDUCATION,

    @SerialName("Развлечения")
    ENTERTAINMENT,

    @SerialName("Музыка")
    MUSIC,

//    @SerialName("Фото и видео")
//    VIDEO,

    @SerialName("Фото и видео")
    PHOTOGRAPHY,

    @SerialName("Здоровье и фитнес")
    HEALTH,

    @SerialName("Спорт")
    SPORTS,

    @SerialName("Новости")
    NEWS,

    @SerialName("Книги и справочники")
    BOOKS,

    @SerialName("Бизнес")
    BUSINESS,

    @SerialName("Финансы")
    FINANCE,

    @SerialName("Образ жизни")
    LIFESTYLE,

    @SerialName("Путешествия")
    TRAVEL,

    @SerialName("Навигация")
    MAPS,

    @SerialName("Еда и напитки")
    FOOD,

    @SerialName("Шопинг")
    SHOPPING,

    @SerialName("Утилиты")
    UTILITIES,
    
    @SerialName("Погода")
    WEATHER,
}