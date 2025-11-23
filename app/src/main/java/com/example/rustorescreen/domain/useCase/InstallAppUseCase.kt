package com.example.rustorescreen.domain.useCase

import com.example.rustorescreen.domain.repositoryInterface.InstallAppRepository
import com.example.rustorescreen.domain.domainModel.InstallStatus
import kotlinx.coroutines.flow.Flow

class InstallAppUseCase(
    private val installAppRepository: InstallAppRepository,
) {
    operator fun invoke(): Flow<InstallStatus> {
        return installAppRepository.installApk()
    }
}