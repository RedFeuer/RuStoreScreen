package com.example.rustorescreen.data.mapper

import com.example.rustorescreen.data.dto.AppDetailsDto
import com.example.rustorescreen.domain.domainModel.AppDetails

/**
 * Mapper для преобразования DTO приложения в доменную модель.
 *
 * Используется для изоляции доменного слоя от слоя данных — копирует поля из
 * [AppDetailsDto] в [AppDetails].
 */
class AppDetailsMapper {
    /**
     * Преобразует [AppDetailsDto] в [AppDetails].
     *
     * @param dto DTO с данными приложения (не nullable!).
     * @return доменная модель [AppDetails] с соответствующими полями.
     */
    fun toDomainModel(dto: AppDetailsDto): AppDetails = AppDetails (
        id = dto.id,
        name = dto.title,
        installStatus = dto.installStatus,
        hasInstallAttempts = dto.hasInstallAttempts,
        developer = dto.developer,
        category = dto.category,
        ageRating = dto.ageRating,
        size = dto.size,
        iconUrl = dto.iconUrl,
        screenshotUrlList = dto.screenshotUrlList,
        description = dto.description
    )
}