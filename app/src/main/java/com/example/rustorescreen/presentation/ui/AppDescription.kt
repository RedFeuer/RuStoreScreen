package com.example.rustorescreen.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.rustorescreen.R

/**
 * Компонент описания приложения с возможностью развернуть/свернуть текст.
 *
 * @param description Текст описания приложения.
 * @param expanded Флаг, указывающий, развернут ли текст. Если `true` — показываются все строки,
 *                 если `false` — ограничено двумя строками.
 * @param onExpandClick Колбэк, вызываемый при нажатии на кнопку развернуть/свернуть.
 * @param modifier Модификатор для внешнего контейнера.
 */
@Composable
fun AppDescription(
    description: String,
    expanded: Boolean,
    onExpandClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        Text(text = stringResource(R.string.app_details_description))
        Spacer(Modifier.height(8.dp))
        Text(
            text = description,
            style = MaterialTheme.typography.bodyLarge,
            // Показываем все строки, когда развернуто; иначе только 2 строки.
            maxLines = if (!expanded) Int.MAX_VALUE else 2,
            overflow = TextOverflow.Ellipsis
        )
        // Кнопка Развернуть/Свернуть
        TextButton(
            onClick = onExpandClick
        ) {
            Text(if (expanded) {
                stringResource(R.string.app_details_read_more)
            } else {
                stringResource(R.string.app_details_read_less)
            })
        }
    }
}