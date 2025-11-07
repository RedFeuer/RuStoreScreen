package com.example.rustorescreen.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.rustorescreen.R

/**
 * Отображает строку с информацией о разработчике приложения.
 *
 * В строке показывается имя разработчика и дополнительный текст из ресурсов
 * (`R.string.app_details_developer` - название студии/разработчика).
 * Вся строка и кнопка-иконка реагируют на нажатие и вызывают переданный `onClick`.
 *
 * @param name Имя разработчика для отображения.
 * @param onClick Коллбэк, срабатывающий при нажатии на строку или иконку.
 * @param modifier Дополнительный [Modifier], применяемый к корневому `Row`.
 */
@OptIn(ExperimentalMaterial3Api::class) // using Material3 experimental API
@Composable
fun Developer(
    name: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.clickable(onClick = onClick),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column {
            Text(text = name)
            Text(text = stringResource(R.string.app_details_developer))
        }
        IconButton(onClick = onClick) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                contentDescription = null,
            )
        }
    }
}