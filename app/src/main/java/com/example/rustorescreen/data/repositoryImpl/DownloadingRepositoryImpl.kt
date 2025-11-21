package com.example.rustorescreen.data.repositoryImpl

import com.example.rustorescreen.domain.repositoryInterface.DownloadingRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DownloadingRepositoryImpl() : DownloadingRepository {
    /* типо скачиваем .apk файл */
    override fun processApk(): Flow<Int> =
        flow {
            for (i in 0 .. 100) {
                delay(100L)
                emit(i)
            }
        }
}