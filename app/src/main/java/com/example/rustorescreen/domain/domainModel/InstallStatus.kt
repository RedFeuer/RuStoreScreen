package com.example.rustorescreen.domain.domainModel

sealed interface InstallStatus {
    data object Idle: InstallStatus // = Uninstalled = состояние по умолчанию
    data object InstallPrepared: InstallStatus
    data object InstallStarted: InstallStatus
    data class Installing(val progress: Int): InstallStatus
    data object Installed: InstallStatus
    data class InstallError(val error: Throwable): InstallStatus

    /* TODO:
    *   1) добавить возможность удаления приложения*/
    data object UninstallPrepared: InstallStatus
    data class Uninstalling(val progress: Int): InstallStatus
    data class UninstallError(val error: Throwable): InstallStatus
}