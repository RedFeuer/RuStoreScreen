package com.example.rustorescreen.presentation.viewModel

sealed interface AppDetailsEvent{
    data object WorkInProgress : AppDetailsEvent
}