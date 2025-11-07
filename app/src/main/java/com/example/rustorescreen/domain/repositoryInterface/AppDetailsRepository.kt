package com.example.rustorescreen.domain.repositoryInterface

import com.example.rustorescreen.domain.domainModel.AppDetails

/**
 * Репозиторий для получения конкретного приложения.
 *
 * Реализация должна предоставлять доступ к данным `AppDetails` по идентификатору.
 */
interface AppDetailsRepository {
    /**
     * Получить детали приложения по идентификатору.
     *
     * @param id идентификатор приложения
     * @return [AppDetails] доменная модель приложения
     * @throws NoSuchElementException если приложение с указанным id не найдено
     */
    suspend fun getById(id: String): AppDetails
}