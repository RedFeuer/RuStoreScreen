package com.example.rustorescreen.presentation.viewModel

/* класс состояния экрана приложения */
sealed interface AppDetailsState {
    data object Error: AppDetailsState
    data object Loading: AppDetailsState


}