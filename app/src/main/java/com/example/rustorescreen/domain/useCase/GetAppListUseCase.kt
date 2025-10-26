package com.example.rustorescreen.domain.useCase

import com.example.rustorescreen.domain.domainModel.AppDetails
import com.example.rustorescreen.domain.repositoryInterface.AppListRepository

// Use case for retrieving App data
class GetAppListUseCase(
    private val appRepository: AppListRepository // Dependency injection of the AppRepository
) {
    operator fun invoke(): List<AppDetails> {
        val apps: List<AppDetails> = appRepository.get()// Fetches the App data from the repository when called

        for (app in apps) {
            if (app.id < 0) {
                throw IllegalArgumentException("App with name: ${app.name} has id: ${app.id}. But id must be non-negative.")
            }
        }

        return apps
    }
}