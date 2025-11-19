package com.example.rustorescreen.domain.useCase

import com.example.rustorescreen.domain.domainModel.AppDetails
import com.example.rustorescreen.domain.repositoryInterface.AppListRepository
import kotlinx.coroutines.flow.Flow

// TODO: переписать документацию после внедрения базы данных

/**
 * Use case для получения списка приложений.
 *
 * Делегирует получение данных репозиторию и служит точкой для размещения
 * бизнес-логики: валидаций, проброса/обработки
 * исключений и т.д.
 *
 * @property appRepository репозиторий, предоставляющий данные о приложениях.
 */
class GetAppListUseCase(
    private val appRepository: AppListRepository // Dependency injection of the AppRepository
) {
    /* вообще тут была похитрее проверка, сейчас по сути просто проксируем метод репозитория.get()
    * ЛОГИЧЕСКИЕ ПРОВЕРКИ, ПРОБРОСЫ ИСКЛЮЧЕНИЙ И ВСЯКОЕ ТАКОЕ ДОБАВЛЯТЬ СЮДА*/

    /**
     * Выполняет получение списка приложений.
     *
     * Текущая реализация просто проксирует вызов к методу [AppListRepository.get].
     * Рекомендации для расширения логики(на будущее):
     *  - добавить валидацию/фильтрацию данных;
     *  - обработку ошибок (оборачивать/логировать исключения);
     *
     * Метод является `suspend` и рассчитан на вызов из Coroutines.
     *
     * @return список [AppDetails]
     * @throws Exception пробрасывает исключения, возникающие в репозитории;
     *         при необходимости оборачивать здесь.
     */
    operator fun invoke(): Flow<List<AppDetails>> {
        val apps: Flow<List<AppDetails>> = appRepository.get()// Fetches the App data from the repository when called

        return apps
    }
}