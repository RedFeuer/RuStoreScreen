package com.example.rustorescreen.domain.useCase

import com.example.rustorescreen.domain.domainModel.AppDetails
import com.example.rustorescreen.domain.repositoryInterface.AppDetailsRepository

class GetAppDetailsUseCase(
    private val appDetailsRepository: AppDetailsRepository
) {
    suspend operator fun invoke(id: Int) : AppDetails {
        val appDetails: AppDetails = appDetailsRepository.getById(id)

        if (appDetails.id < 0) {
            throw IllegalArgumentException("App with name: ${appDetails.name} has id: ${appDetails.id}. But id must be non-negative.")
        }

        return appDetails
    }
}