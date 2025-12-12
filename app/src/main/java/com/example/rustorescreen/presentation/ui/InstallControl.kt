package com.example.rustorescreen.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.rustorescreen.R
import com.example.rustorescreen.domain.domainModel.AppDetails
import com.example.rustorescreen.domain.domainModel.InstallStatus
import com.example.rustorescreen.presentation.viewModel.AppDetailsState


@Composable
fun InstallControl(
    content: AppDetailsState.Content,
    installActions: InstallActions,
    modifier: Modifier = Modifier
) {
    val app: AppDetails = content.appDetails
    when (val currentInstallStatus = app.installStatus) {
        is InstallStatus.Idle -> {
            /* состояние загрузки */
            InstallButton(
                content = content,
                onClick = installActions.install, // = viewModel.installApp()
                modifier = modifier,
            )
        }
        is InstallStatus.Installed -> {
//        is InstallStatus.UninstallPrepared,
//        is InstallStatus.Installing,
//        is InstallStatus.UninstallError -> {
            /* TODO: реализовать на изменени панели, чтобы теперь появлялось окошко
                *   открыть и удалить*/
            UninstallButton(
                content = content,
                onUninstallClick = installActions.uninstall, // = viewModel.uninstallApp()
                onOpenClick = installActions.open, // = viewModel.showWorkInProgressMessage
                modifier = modifier
            )
        }
        else -> {
            /* состояние ошибки или прерывания загрузки */
            ReinstallButton(
                content = content,
                onClick = installActions.reinstall, // = viewModel.installApp()
                modifier = modifier,
            )
        }
    }
}

@Composable
fun InstallButton(
    content: AppDetailsState.Content,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val app: AppDetails = content.appDetails
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        contentPadding = PaddingValues(vertical = 8.dp),
        modifier = modifier,
    ) {
        when (val currentInstallStatus = app.installStatus) {
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
                Text(text = stringResource(R.string.installed))
            }
            else -> {
                Text(text = stringResource(R.string.work_in_progress))
            }
        }
    }
}

/* TODO: сверстать
*   тут надо поменять название, и надо типо column(button, button)
*   чтобы было две кнопки - Удалить, Открыть в одной строчке*/
@Composable
fun UninstallButton(
    content: AppDetailsState.Content,
    onUninstallClick: () -> Unit,
    onOpenClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val app: AppDetails = content.appDetails
    val currentInstallStatus = app.installStatus

    when (currentInstallStatus) {
        is InstallStatus.Installed -> {
            /* кнопки удалить удалить - открыть */
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = modifier
            ) {
                /* кнопка удаления приложения */
                Button(
                    onClick = onUninstallClick,
                    shape = RoundedCornerShape(16.dp),
                    contentPadding = PaddingValues(vertical = 8.dp),
                    modifier = modifier.weight(1f)
                ) {
                    Text(text = stringResource(R.string.uninstall))
                }

                /* WIP: кнопка открыть приложение */
                Button(
                    onClick = onOpenClick,
                    shape = RoundedCornerShape(16.dp),
                    contentPadding = PaddingValues(vertical = 8.dp),
                    modifier = modifier.weight(1f)
                ) {
                    Text(text = stringResource(R.string.open))
                }
            }
        }
        else -> {
            /* кнопка, на который пишется статус удаления */
            Button(
                onClick = onUninstallClick,
                shape = RoundedCornerShape(16.dp),
                contentPadding = PaddingValues(vertical = 8.dp),
                modifier = modifier,
            ) {
                when (currentInstallStatus) {
                    is InstallStatus.UninstallPrepared -> {
                        Text(text = stringResource(R.string.uninstallPrepared))
                    }
                    is InstallStatus.Uninstalling -> {
                        Text(text = stringResource(R.string.uninstalling))
                    }
                    else -> {
                        Text(text = stringResource(R.string.work_in_progress))
                    }
                }
            }
        }
    }
}

@Composable
fun ReinstallButton(
    content: AppDetailsState.Content,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val app: AppDetails = content.appDetails

    var showPreviousInstallationProgress by remember { mutableStateOf(true) }

    Button(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        contentPadding = PaddingValues(vertical = 8.dp),
        modifier = modifier,
    ) {
        val currentInstallStatus: InstallStatus = app.installStatus

        /* определяем, нужно ли на этом статусе загрузки показывать, что был предыдущий прогресс */
        val shouldShowPreviousProgress: Boolean = when(currentInstallStatus) {
            is InstallStatus.InstallPrepared -> true
            is InstallStatus. InstallStarted -> true
            is InstallStatus.Installing -> true
            else -> false
        }

        /* показываем предыдущий прогресс, если надо(если он был прерван в прошлый раз) */
        if (shouldShowPreviousProgress && showPreviousInstallationProgress) {
            Text(text = stringResource(R.string.previousProgress))
            showPreviousInstallationProgress = false
        }

        when (val currentInstallStatus = app.installStatus) {
            is InstallStatus.Idle -> {
                Text(text = stringResource(R.string.reinstall))
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
                Text(text = stringResource(R.string.installed))
            }
            else -> {
                Text(text = stringResource(R.string.work_in_progress))
            }
        }
    }
}