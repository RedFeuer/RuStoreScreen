package com.example.rustorescreen.presentation.viewModel

import com.example.rustorescreen.domain.domainModel.AppDetails

/**
 * Класс состояния экрана приложения.
 *
 * Используется для представления трёх основных состояний:
 *  - Error — ошибка при загрузке данных;
 *  - Loading — данные загружаются;
 *  - Content — данные успешно загружены.
 */
sealed interface AppDetailsState {
    /**
     * Состояние ошибки загрузки данных приложения.
     */
    data object Error: AppDetailsState
    /**
     * Состояние загрузки данных приложения.
     */
    data object Loading: AppDetailsState

    /**
     * Состояние содержимого с данными приложения.
     * Добавляя новые поля состояния, можно расширять функционал
     *
     * @property appDetails данные приложения из доменной модели
     * @property descriptionExpanded флаг, указывающий, раскрыто ли длинное описание
     */
    data class Content(
        val appDetails: AppDetails,
        val descriptionExpanded: Boolean = false,
    ) : AppDetailsState

}