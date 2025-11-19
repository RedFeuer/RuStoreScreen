package com.example.rustorescreen.domain.useCase

import com.example.rustorescreen.domain.domainModel.AppCategory
import com.example.rustorescreen.domain.repositoryInterface.AppDetailsRepository

class UpdateAppCategoryUseCase(
    private val appDetailsRepository: AppDetailsRepository,
) {
    suspend operator fun invoke(id: String, newCategory: AppCategory) {
        appDetailsRepository.setAppCategory(id = id, newCategory = newCategory)
    }
}