package com.example.rustorescreen.domain.useCase

import com.example.rustorescreen.domain.domainModel.InstallStatus
import com.example.rustorescreen.domain.repositoryInterface.InstallAppRepository
import kotlinx.coroutines.flow.Flow

class CancelAppUseCase(
    private val installAppRepository: InstallAppRepository
) {
    operator fun invoke(id: String): Flow<InstallStatus> {
        return installAppRepository.cancelApk(id)
    }
}