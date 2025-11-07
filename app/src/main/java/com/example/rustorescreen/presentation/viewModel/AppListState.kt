package com.example.rustorescreen.presentation.viewModel

import com.example.rustorescreen.domain.domainModel.AppDetails

/**
 * Состояние экрана со списком приложений.
 *
 * Представляет возможные состояния UI при получении списка приложений:
 * - Error — ошибка при загрузке данных;
 * - Loading — данные загружаются;
 * - Content — данные успешно загружены.
 */
sealed interface AppListState {
    /**
     * Состояние загрузки списка приложений.
     */
    data object Loading: AppListState
    /**
     * Состояние ошибки загрузки списка приложений.
     */
    data object Error: AppListState

    /**
     * Состояние с успешно загруженным списком приложений.
     * При необходимости сюда можно добавлять поля.
     *
     * @property appList список моделей AppDetails для отображения
     */
    data class Content(
        val appList: List<AppDetails>,
    ) : AppListState
}