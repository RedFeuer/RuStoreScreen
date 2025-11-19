package com.example.rustorescreen.domain.repositoryInterface

import com.example.rustorescreen.domain.domainModel.AppDetails
import kotlinx.coroutines.flow.Flow

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
     * @return Flow<AppDetails> доменная модель приложения
     * @throws NoSuchElementException если приложение с указанным id не найдено
     */
    fun getById(id: String): Flow<AppDetails>
}