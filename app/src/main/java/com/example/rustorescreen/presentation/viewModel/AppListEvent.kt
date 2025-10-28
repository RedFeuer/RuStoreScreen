package com.example.rustorescreen.presentation.viewModel

sealed interface AppListEvent {
    data class TapOnIcon(val messageResId: Int) : AppListEvent
}