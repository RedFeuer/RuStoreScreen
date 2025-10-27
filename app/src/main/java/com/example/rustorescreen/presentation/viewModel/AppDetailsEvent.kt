package com.example.rustorescreen.presentation.viewModel

import androidx.annotation.StringRes
import androidx.compose.ui.text.AnnotatedString

sealed interface AppDetailsEvent{
    data class WorkInProgress(val messageResId: Int) : AppDetailsEvent
}