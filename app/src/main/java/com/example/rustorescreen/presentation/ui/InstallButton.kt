package com.example.rustorescreen.presentation.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.rustorescreen.R

/**
 * Компонент кнопки установки приложения.
 *
 * @param onClick действие, выполняемое при нажатии на кнопку(установка - WIP).
 * @param modifier модификатор для настройки внешнего вида и расположения кнопки.
 */
@Composable
fun InstallButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        contentPadding = PaddingValues(vertical = 8.dp),
        modifier = modifier,
    ) {
        Text(text = stringResource(R.string.install))
    }
}