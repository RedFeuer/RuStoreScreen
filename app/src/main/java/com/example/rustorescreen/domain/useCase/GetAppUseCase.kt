package com.example.rustorescreen.domain.useCase

import com.example.rustorescreen.domain.domainModel.App
import com.example.rustorescreen.domain.repositoryInterface.AppRepository

// Use case for retrieving App data
class GetAppUseCase(
    private val appRepository: AppRepository // Dependency injection of the AppRepository
) {
    operator fun invoke(): List<App> {
        val apps: List<App> = appRepository.get()// Fetches the App data from the repository when called

        for (app in apps) {
            if (app.id < 0) {
                throw IllegalArgumentException("App ID cannot be negative: ${app.id}")
            }
        }

        return apps
    }
}