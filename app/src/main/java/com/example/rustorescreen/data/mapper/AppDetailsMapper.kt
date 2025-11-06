package com.example.rustorescreen.data.mapper

import com.example.rustorescreen.data.dto.AppDetailsDto
import com.example.rustorescreen.domain.domainModel.AppDetails

class AppDetailsMapper {
    fun toDomainModel(dto: AppDetailsDto): AppDetails = AppDetails (
        name = dto.title,
        developer = dto.developer,
        category = dto.category,
        ageRating = dto.ageRating,
        size = dto.size,
        iconUrl = dto.iconUrl,
        screenshotUrlList = dto.screenshotUrlList,
        description = dto.description
    )

}