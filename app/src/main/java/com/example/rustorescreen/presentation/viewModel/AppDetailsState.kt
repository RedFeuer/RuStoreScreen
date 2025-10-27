package com.example.rustorescreen.presentation.viewModel

import com.example.rustorescreen.domain.domainModel.AppDetails

/* класс состояния экрана приложения */
sealed interface AppDetailsState {
    /* состояние ошибки загрузки данных приложения */
    data object Error: AppDetailsState
    /* состояние загрузки данных приложения */
    data object Loading: AppDetailsState

    /* состояние с данными приложения */
    /* здесь можно расширять функционал, добавляя новые поля состояния */
    data class Content(
        val appDetails: AppDetails,
        val descriptionExpanded: Boolean = false,
    ) : AppDetailsState

}