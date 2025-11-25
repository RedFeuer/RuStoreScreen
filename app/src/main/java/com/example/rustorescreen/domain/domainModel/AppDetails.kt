package com.example.rustorescreen.domain.domainModel

/**
 * Модель с деталями приложения.
 *
 * @property id Уникальный идентификатор приложения.
 * @property name Название приложения.
 * @property developer Название разработчика или студии.
 * @property category Категория приложения (например, GAME, APP).
 * @property ageRating Возрастной рейтинг (например, 3, 7, 12, 16, 18).
 * @property size Размер приложения в мегабайтах.
 * @property iconUrl URL иконки приложения.
 * @property screenshotUrlList Список URL-адресов скриншотов приложения.
 * @property description Краткое описание приложения.
 */
data class AppDetails(
    val id: String,
    val name: String,
    val installStatus: InstallStatus = InstallStatus.Idle, // по умолчанию - не установлено
    val developer: String,
    val category: AppCategory,
    val ageRating: Int,
    val size: Float,
    val iconUrl: String,
    val screenshotUrlList: List<String>,
    val description: String,
)