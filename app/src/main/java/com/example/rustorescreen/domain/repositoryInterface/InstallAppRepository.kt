package com.example.rustorescreen.domain.repositoryInterface

import com.example.rustorescreen.presentation.viewModel.InstallStatus
import kotlinx.coroutines.flow.Flow

interface InstallAppRepository {
    fun installApk(): Flow<InstallStatus>
}