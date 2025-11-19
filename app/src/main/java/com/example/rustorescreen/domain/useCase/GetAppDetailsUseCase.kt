package com.example.rustorescreen.domain.useCase

import com.example.rustorescreen.domain.domainModel.AppDetails
import com.example.rustorescreen.domain.repositoryInterface.AppDetailsRepository
import kotlinx.coroutines.flow.Flow

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
     * Текущая реализация просто проксирует вызов к методу [AppDetailsRepository.getById].
     * Рекомендации для расширения логики(на будущее):
     *  - добавить валидацию/фильтрацию данных;
     *  - обработку ошибок (оборачивать/логировать исключения);
     *
     * Метод является `suspend` и рассчитан на вызов из Coroutines.
     *
     * @param id уникальный идентификатор приложения
     * @return [Flow<AppDetails>] с информацией о приложении
     * @throws NoSuchElementException если приложение с указанным id не найдено
     */
    operator fun invoke(id: String) : Flow<AppDetails> {
        val appDetails: Flow<AppDetails> = appDetailsRepository.getById(id)
        return appDetails
    }
}