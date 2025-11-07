package com.example.rustorescreen.domain.useCase

import com.example.rustorescreen.domain.domainModel.AppDetails
import com.example.rustorescreen.domain.repositoryInterface.AppDetailsRepository

/**
 * Use case для получения конкретного приложения по идентификатору.
 *
 * @property appDetailsRepository репозиторий, отвечающий за загрузку данных приложения
 */
class GetAppDetailsUseCase(
    private val appDetailsRepository: AppDetailsRepository
) {
    /**
     * Выполняет получение конкретного приложения.
     *
     * @param id уникальный идентификатор приложения
     * @return [AppDetails] с информацией о приложении
     * @throws NoSuchElementException если приложение с указанным id не найдено
     */
    suspend operator fun invoke(id: String) : AppDetails {
        val appDetails: AppDetails = appDetailsRepository.getById(id)
        return appDetails
    }
}