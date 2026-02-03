package com.example.rustorescreen.presentation.ui

/*  */
sealed interface InstallCommand {
    data class Install(val appId: String): InstallCommand
    data class Uninstall(val appId: String): InstallCommand
    data class Cancel(val appId: String): InstallCommand
}