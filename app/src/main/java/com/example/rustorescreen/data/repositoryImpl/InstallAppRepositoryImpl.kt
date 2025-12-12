package com.example.rustorescreen.data.repositoryImpl

import com.example.rustorescreen.data.api.ApkUrlApi
import com.example.rustorescreen.domain.domainModel.InstallStatus
import com.example.rustorescreen.domain.repositoryInterface.AppDetailsRepository
import com.example.rustorescreen.domain.repositoryInterface.DownloadingRepository
import com.example.rustorescreen.domain.repositoryInterface.InstallAppRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class InstallAppRepositoryImpl @Inject constructor(
    private val appDetailsRepository: AppDetailsRepository,
    private val apkUrlApi: ApkUrlApi,
    private val downloadingRepository: DownloadingRepository,
) : InstallAppRepository {
    override fun installApk(id: String): Flow<InstallStatus> =
        flow {
            emit(InstallStatus.InstallPrepared) // изменения на экран
            apkUrlApi.preparingApk()
            withContext(Dispatchers.IO) {
                appDetailsRepository.setInstallStatus(id = id, newInstallStatus = InstallStatus.InstallPrepared) // изменения в БД
            }

            /* получаем URL для загрузки .apk файла из сети(API) */
            apkUrlApi.getApk()
            emit(InstallStatus.InstallStarted)
            withContext(Dispatchers.IO) {
                appDetailsRepository.setInstallStatus(id = id, newInstallStatus = InstallStatus.InstallStarted) // изменения в БД
            }

            /* отправляем все изменения при загрузке(от 1 до 100) .apk файла */
            emitAll(
                flow = downloadingRepository.processApk()
                    .map{ progress -> // преобразуем Flow<Int> -> Flow<InstallStatus>
                        if (progress % 20 == 0) {
                            withContext(Dispatchers.IO) {
                                appDetailsRepository.setInstallStatus(id = id, newInstallStatus = InstallStatus.Installing(progress)) // изменения в БД
                            }
                        }

                        InstallStatus.Installing(progress)
                    }
            )

            emit(InstallStatus.Installed)
            withContext(Dispatchers.IO) {
                appDetailsRepository.setInstallStatus(id = id, newInstallStatus = InstallStatus.Installed)
            }
        }
            .catch { error ->
                emit(InstallStatus.InstallError(error))
                withContext(Dispatchers.IO) {
                    appDetailsRepository.setInstallStatus(id = id, newInstallStatus = InstallStatus.InstallError(error))
                }
            }

    override fun uninstallApk(id: String): Flow<InstallStatus> =
        flow {
            emit(InstallStatus.UninstallPrepared)
            withContext(Dispatchers.IO) {
                appDetailsRepository.setInstallStatus(id = id, newInstallStatus = InstallStatus.UninstallPrepared)
            }
            apkUrlApi.preparingApk()

            emit(InstallStatus.Uninstalling)
            withContext(Dispatchers.IO) {
                appDetailsRepository.setInstallStatus(id = id, newInstallStatus = InstallStatus.Uninstalling)
            }
            apkUrlApi.deleteApi()

            emit(InstallStatus.Idle)
            withContext(Dispatchers.IO) {
                appDetailsRepository.setInstallStatus(id = id, newInstallStatus = InstallStatus.Idle)
            }
        }
            .catch{ error ->
                emit(InstallStatus.UninstallError(error))
                withContext(Dispatchers.IO) {
                    appDetailsRepository.setInstallStatus(id = id, newInstallStatus = InstallStatus.UninstallError(error))
                }
            }
}