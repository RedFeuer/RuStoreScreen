package com.example.rustorescreen.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


/**
 * Отображает центрированный индикатор загрузки для экрана конкретного приложения.
 *
 * Используется для показа состояния загрузки данных.
 *
 * @param modifier Modifier для настройки размеров, отступов и позиционирования контейнера.
 */
@Composable
fun AppDetailsLoading (modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}
