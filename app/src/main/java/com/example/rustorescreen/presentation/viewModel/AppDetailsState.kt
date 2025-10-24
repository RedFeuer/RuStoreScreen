package com.example.rustorescreen.presentation.viewModel

import com.example.rustorescreen.domain.domainModel.AppDetails

/* класс состояния экрана приложения */
sealed interface AppDetailsState {
    /* состояние ошибки загрузки данных приложения */
    data object Error: AppDetailsState
    /* состояние загрузки данных приложения */
    data object Loading: AppDetailsState

    /* состояние с данными приложения */
    data class Content(
        val appDetails: AppDetails,
        val descriptionExpanded: Boolean,
    ) : AppDetailsState

}