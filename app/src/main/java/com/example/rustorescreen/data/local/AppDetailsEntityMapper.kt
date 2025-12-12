package com.example.rustorescreen.data.local

import com.example.rustorescreen.domain.domainModel.AppDetails

class AppDetailsEntityMapper {
    fun toDomainModel(entity: AppDetailsEntity): AppDetails = AppDetails (
        id = entity.id,
        name = entity.title,
        installStatus = entity.installStatus,
        hasInstallAttempts = entity.hasInstallAttempts,
        developer = entity.developer,
        category = entity.category,
        ageRating = entity.ageRating,
        size = entity.size,
        iconUrl = entity.iconUrl,
        screenshotUrlList = entity.screenshotUrlList,
        description = entity.description
    )

    fun toEntity(domainModel: AppDetails): AppDetailsEntity = AppDetailsEntity (
        id = domainModel.id,
        title = domainModel.name,
        installStatus = domainModel.installStatus,
        hasInstallAttempts = domainModel.hasInstallAttempts,
        developer = domainModel.developer,
        category = domainModel.category,
        ageRating = domainModel.ageRating,
        size = domainModel.size,
        iconUrl = domainModel.iconUrl,
        screenshotUrlList = domainModel.screenshotUrlList,
        description = domainModel.description
    )
}