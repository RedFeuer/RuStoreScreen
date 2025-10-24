package com.example.rustorescreen.data.mapper

import com.example.rustorescreen.data.dto.AppDto
import com.example.rustorescreen.domain.domainModel.AppDetails

class AppMapper {
    fun toDomainModel(dto: AppDto): AppDetails = AppDetails (
        id = dto.id,
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