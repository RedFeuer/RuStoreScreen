package com.example.rustorescreen.domain.repositoryInterface

import com.example.rustorescreen.domain.domainModel.InstallStatus
import kotlinx.coroutines.flow.Flow

interface InstallAppRepository {
    fun installApk(id: String): Flow<InstallStatus>

    fun uninstallApk(id: String): Flow<InstallStatus>
}