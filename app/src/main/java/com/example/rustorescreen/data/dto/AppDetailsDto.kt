package com.example.rustorescreen.data.dto

import com.example.rustorescreen.domain.domainModel.AppCategory
import com.example.rustorescreen.domain.domainModel.InstallStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO для подробной информации о приложении.
 *
 * Используется для сериализации/десериализации в/из JSON при обмене данными с сервером.
 *
 * @property id Уникальный идентификатор приложения.
 * @property title Название приложения.
 * @property developer Название разработчика или студии.
 * @property category Категория приложения (например, GAME, APP).
 * @property ageRating Возрастной рейтинг (например, 3, 7, 12, 16, 18).
 * @property size Размер приложения в мегабайтах.
 * @property iconUrl URL иконки приложения.
 * @property screenshotUrlList Список URL-адресов скриншотов приложения.
 * @property description Краткое описание приложения.
 */
@Serializable
data class AppDetailsDto(

    val id: String = "",

    @SerialName("name")
    val title: String = "",
    val installStatus: InstallStatus = InstallStatus.Idle,
    val hasInstallAttempts: Boolean = false,

    val developer: String = "",
    val category: AppCategory = AppCategory.APP,
    val ageRating: Int = 0,
    val size: Float = 0.0.toFloat(),
    val iconUrl: String = "",
    val screenshotUrlList: List<String> = emptyList<String>(),
    val description: String = "",
)
