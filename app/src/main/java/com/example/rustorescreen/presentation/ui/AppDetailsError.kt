package com.example.rustorescreen.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.rustorescreen.R

/**
 * Отображает состояние ошибки загрузки конкретного приложения.
 *
 * @param onRefreshClick Lambda, вызываемая при нажатии на кнопку обновления.
 * @param modifier Модификатор для внешнего контейнера компонента.
 */
@Composable
fun AppDetailsError(
    onRefreshClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column (
        modifier = modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Текст с описанием ошибки
        Text(
            text = stringResource(R.string.app_details_error),
            fontStyle = MaterialTheme.typography.headlineMedium.fontStyle,
        )

        Spacer(Modifier.height(12.dp))

        // Кнопка для повторной попытки загрузить экран приложения
        Button(
            onClick = onRefreshClick,
        ) {
            Text(text = stringResource(R.string.app_details_error_refresh) )
        }
    }
}