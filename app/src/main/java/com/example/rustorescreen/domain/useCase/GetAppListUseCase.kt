package com.example.rustorescreen.domain.useCase

import com.example.rustorescreen.domain.domainModel.AppDetails
import com.example.rustorescreen.domain.repositoryInterface.AppListRepository

// Use case for retrieving App data
class GetAppListUseCase(
    private val appRepository: AppListRepository // Dependency injection of the AppRepository
) {
    suspend operator fun invoke(): List<AppDetails> {
        val apps: List<AppDetails> = appRepository.get()// Fetches the App data from the repository when called

        return apps
    }
}