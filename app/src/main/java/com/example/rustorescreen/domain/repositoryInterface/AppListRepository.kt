package com.example.rustorescreen.domain.repositoryInterface

import com.example.rustorescreen.domain.domainModel.AppDetails
import kotlinx.coroutines.flow.Flow

// TODO: переписать документацию после внедрения базы данных
/**
 * Репозиторий для получения списка приложений.
 *
 * Реализация возвращает список объектов [AppDetails].
 * Метод является suspend-функцией и должен вызываться из корутины или соответствующего контекста(suspend).
 */
interface AppListRepository {
    /**
     * Получить список приложений.
     *
     * Выполняется в корутине (`suspend`).
     *
     * @return список `AppDetails` — доменные модели приложений.
     */
    suspend fun get(): Flow<List<AppDetails>>
}