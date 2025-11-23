package com.example.rustorescreen.data.repositoryImpl

import com.example.rustorescreen.data.api.ApkUrlApi
import com.example.rustorescreen.domain.repositoryInterface.DownloadingRepository
import com.example.rustorescreen.domain.repositoryInterface.InstallAppRepository
import com.example.rustorescreen.domain.domainModel.InstallStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class InstallAppRepositoryImpl @Inject constructor(
    private val apkUrlApi: ApkUrlApi,
    private val downloadingRepository: DownloadingRepository,
) : InstallAppRepository {
    override fun installApk(): Flow<InstallStatus> =
        flow {
            emit(InstallStatus.InstallPrepared)

            /* получаем URL для загрузки .apk файла из сети(API) */
            apkUrlApi.getApk()
            emit(InstallStatus.InstallStarted)

            /* отправляем все изменения при загрузке(от 1 до 100) .apk файла */
            emitAll(
                flow = downloadingRepository.processApk()
                    .map{ progress ->
                        InstallStatus.Installing(progress)
                    }
            )

            emit(InstallStatus.Installed)
        }
            .catch { error ->
                emit(InstallStatus.InstallError(error))
            }
}