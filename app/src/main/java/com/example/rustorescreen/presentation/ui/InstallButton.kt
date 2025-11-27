package com.example.rustorescreen.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.rustorescreen.R
import com.example.rustorescreen.presentation.viewModel.AppDetailsState
import com.example.rustorescreen.domain.domainModel.InstallStatus

/**
 * Компонент кнопки установки приложения.
 *
 * @param onClick действие, выполняемое при нажатии на кнопку(установка - WIP).
 * @param modifier модификатор для настройки внешнего вида и расположения кнопки.
 */
@Composable
fun InstallButton(
    content: AppDetailsState.Content,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        contentPadding = PaddingValues(vertical = 8.dp),
        modifier = modifier,
    ) {
        when (val currentInstallStatus = content.appDetails.installStatus) {
            is InstallStatus.Idle -> {
                Text(text = stringResource(R.string.install))
            }
            is InstallStatus.InstallPrepared -> {
                Text(text = stringResource(R.string.installPrepared))
            }
            is InstallStatus.InstallStarted -> {
                Text(text = stringResource(R.string.installStarted))
            }
            is InstallStatus.Installing -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = modifier,
                ) {
                    /* Вывод в формате:
                    * Загрузки: <Количество процентов> %*/
                    Text(text = stringResource(R.string.installing))
                    Spacer(Modifier.height(2.dp))
                    Text(text = currentInstallStatus.progress.toString() + '%')
                }
            }
            is InstallStatus.Installed -> {
                /* TODO: реализовать на изменени панели, чтобы теперь появлялось окошко
                *   открыть и удалить*/
                Text(text = stringResource(R.string.installed))
            }
            /* TODO: реализовать удаление*/
            else -> {
                Text(text = stringResource(R.string.work_in_progress))
            }
        }
    }
}