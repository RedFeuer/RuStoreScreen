package com.example.rustorescreen.domain.repositoryInterface

import kotlinx.coroutines.flow.Flow

interface DownloadingRepository {
    fun processApk(): Flow<Int>
}