package com.example.rustorescreen.presentation.ui

data class InstallActions(
    val install: () -> Unit,
    val uninstall: () -> Unit,
    val reinstall: () -> Unit,
)
