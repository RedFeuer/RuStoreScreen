package com.example.rustorescreen.domain.useCase

import com.example.rustorescreen.domain.domainModel.AppDetails
import com.example.rustorescreen.domain.repositoryInterface.AppDetailsRepository

class GetAppDetailsUseCase(
    private val appDetailsRepository: AppDetailsRepository
) {
    suspend operator fun invoke(id: String) : AppDetails {
        val appDetails: AppDetails = appDetailsRepository.getById(id)

        return appDetails
    }
}