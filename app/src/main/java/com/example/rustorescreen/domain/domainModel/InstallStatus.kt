package com.example.rustorescreen.domain.domainModel

sealed interface InstallStatus {
    /* установка приложения */
    data object Idle: InstallStatus // = Uninstalled = состояние по умолчанию
    data object InstallPrepared: InstallStatus
    data object InstallStarted: InstallStatus
    data class Installing(val progress: Int): InstallStatus
    data object Installed: InstallStatus
    data class InstallError(val error: Throwable): InstallStatus

    /* удаление приложения */
    data object UninstallPrepared: InstallStatus
    data object Uninstalling: InstallStatus
    data class UninstallError(val error: Throwable): InstallStatus
}