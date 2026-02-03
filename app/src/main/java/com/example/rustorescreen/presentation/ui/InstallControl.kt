package com.example.rustorescreen.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
fun InstallationControl(
    content: AppDetailsState.Content,
    installActions: InstallActions,
    modifier: Modifier = Modifier
) {
    val app: AppDetails = content.appDetails
    val currentInstallStatus = app.installStatus
    when (currentInstallStatus) {
        is InstallStatus.Idle,
        is InstallStatus.InstallPrepared,
        is InstallStatus.InstallStarted,
        is InstallStatus.Installing -> {
            Installation(
                content = content,
                onInstallClick = installActions.install, // = viewModel.installApp()
                onCancelClick = installActions.cancel, // = viewModel.cancelInstall()
                modifier = modifier,
            )
        }

        is InstallStatus.Installed,
        is InstallStatus.UninstallPrepared,
        is InstallStatus.Uninstalling -> {
            Uninstallation(
                content = content,
                onUninstallClick = installActions.uninstall, // = viewModel.uninstallApp()
                onOpenClick = installActions.open, // = viewModel.showWorkInProgressMessage
                modifier = modifier
            )
        }

        else -> { // ошибка установки или удаления, возможно лучше убрать и обрабатывать внутри кнопок
            Text(text = "Некая ошибка") // скорее всего ошибка и не нужна
        }

    }
}

@Composable
fun Uninstallation(
    content: AppDetailsState.Content,
    onUninstallClick: () -> Unit,
    onOpenClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val app: AppDetails = content.appDetails

    val currentUninstallStatus: InstallStatus = app.installStatus
    val isUninstallingState: Boolean = when(currentUninstallStatus) {
        is InstallStatus.Installed -> false
        else -> true
    }

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
            UninstallButtonContent(uninstallStatus = currentUninstallStatus)
        }

        /* WIP: кнопка открыть приложение */
        if (!isUninstallingState) {
            Button(
                onClick = onOpenClick,
                shape = RoundedCornerShape(16.dp),
                contentPadding = PaddingValues(vertical = 8.dp),
                modifier = modifier.weight(1f)
            ) {
                OpenButtonContent()
            }
        }
    }
}

@Composable
fun Installation(
    content: AppDetailsState.Content,
    onInstallClick: () -> Unit,
    onCancelClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val app: AppDetails = content.appDetails
    val hasInstallAttempts: Boolean = app.hasInstallAttempts

    /* как начальное состояние записываем hasInstallAttempts
    * в этом поле хранится информация о том, была ли прервана установка до этого
    * если да - то выводится сообщение о перезагрузке и информация о предыдущем состоянии
    * также при переходе от одного этапа к другому пропадет информация о предыдущем состоянии
    * если нет - то просто обычный процесс установки */
    var showPreviousInstallationProgress by remember { mutableStateOf(hasInstallAttempts) }

    val currentInstallStatus: InstallStatus = app.installStatus

    /* булевый флаг, который отвечает за:
    * 1) показ статуса предыдущего прогресса, если предыдущая загрузка была прервана
    * 2) показ кнопки отмены загрузки при установке
    * true - показываем, false - не показываем*/
    val isInstallingState: Boolean = when(currentInstallStatus) {
        is InstallStatus.InstallPrepared,
        is InstallStatus.InstallStarted,
        is InstallStatus.Installing -> true
        else -> false
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier,
    ) {
        /* статус установки */
        Button(
            onClick = onInstallClick,
            shape = RoundedCornerShape(16.dp),
            contentPadding = PaddingValues(vertical = 8.dp),
            modifier = modifier.weight(1f),
        ) {
            /* показываем предыдущий прогресс, если надо(если он был прерван в прошлый раз) */
            if (isInstallingState && showPreviousInstallationProgress) {
                Text(text = stringResource(R.string.previousProgress))
                showPreviousInstallationProgress = false
            }

            InstallButtonContent(installStatus = currentInstallStatus, modifier = modifier)
        }

        if (isInstallingState) {
            /* кнопка отмены установки */
            CancelButton(onClick = onCancelClick)
        }
    }
}

@Composable
fun InstallButtonContent(
    installStatus: InstallStatus,
    modifier: Modifier = Modifier,
) {
    when (installStatus) {
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
                Text(text = installStatus.progress.toString() + '%')
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

@Composable
fun CancelButton(
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier.size(44.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        ),
    ) {
        Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = stringResource(R.string.cancel),
        )
    }
}

@Composable
fun UninstallButtonContent(
    uninstallStatus: InstallStatus,
) {
    when (uninstallStatus) {
        is InstallStatus.Installed -> {
            Text(text = stringResource(R.string.uninstall))
        }
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

@Composable
fun OpenButtonContent() {
    Text(text = stringResource(R.string.open))
}