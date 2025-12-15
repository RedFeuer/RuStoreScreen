package com.example.rustorescreen.data.repositoryImpl

import com.example.rustorescreen.data.api.ApkUrlApi
import com.example.rustorescreen.di.DispatcherIO
import com.example.rustorescreen.domain.domainModel.InstallStatus
import com.example.rustorescreen.domain.repositoryInterface.AppDetailsRepository
import com.example.rustorescreen.domain.repositoryInterface.DownloadingRepository
import com.example.rustorescreen.domain.repositoryInterface.InstallAppRepository
import kotlinx.coroutines.CoroutineDispatcher
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
    // внедрение зависимости Dicpatchers.IO
    @DispatcherIO private val dispatcherIo: CoroutineDispatcher,
) : InstallAppRepository {
    override fun installApk(id: String): Flow<InstallStatus> =
        flow {
            /* устанавливаем в БД статус, что была попытка установки,
            * чтобы если установка будет прервана, то переустановить потом приложение */
            withContext(dispatcherIo) {
                appDetailsRepository.setHasInstallAttempts(id = id, newHasInstallAttempts = true)
            }

            emit(InstallStatus.InstallPrepared) // изменения на экран
            withContext(dispatcherIo) {
                appDetailsRepository.setInstallStatus(id = id, newInstallStatus = InstallStatus.InstallPrepared) // изменения в БД
            }
            apkUrlApi.preparingApk()

            /* получаем URL для загрузки .apk файла из сети(API) */
            withContext(dispatcherIo) {
                appDetailsRepository.setInstallStatus(id = id, newInstallStatus = InstallStatus.InstallStarted) // изменения в БД
            }
            emit(InstallStatus.InstallStarted)
            apkUrlApi.getApk()

            /* отправляем все изменения при загрузке(от 1 до 100) .apk файла */
            emitAll(
                flow = downloadingRepository.processApk()
                    .map{ progress -> // преобразуем Flow<Int> -> Flow<InstallStatus>
                        if (progress % 20 == 0) {
                            withContext(dispatcherIo) {
                                appDetailsRepository.setInstallStatus(id = id, newInstallStatus = InstallStatus.Installing(progress)) // изменения в БД
                            }
                        }

                        InstallStatus.Installing(progress)
                    }
            )

            emit(InstallStatus.Installed)
            withContext(dispatcherIo) {
                appDetailsRepository.setInstallStatus(id = id, newInstallStatus = InstallStatus.Installed)
            }

            /* устанавливаем в БД статус, что попыток установки не было*/
            withContext(dispatcherIo) {
                appDetailsRepository.setHasInstallAttempts(id = id, newHasInstallAttempts = false)
            }
        }
            .catch { error ->
                emit(InstallStatus.InstallError(error))
                withContext(dispatcherIo) {
                    appDetailsRepository.setInstallStatus(id = id, newInstallStatus = InstallStatus.InstallError(error))
                }
            }

    override fun uninstallApk(id: String): Flow<InstallStatus> =
        flow {
            emit(InstallStatus.UninstallPrepared)
            withContext(dispatcherIo) {
                appDetailsRepository.setInstallStatus(id = id, newInstallStatus = InstallStatus.UninstallPrepared)
            }
            apkUrlApi.preparingApk()

            emit(InstallStatus.Uninstalling)
            withContext(dispatcherIo) {
                appDetailsRepository.setInstallStatus(id = id, newInstallStatus = InstallStatus.Uninstalling)
            }
            apkUrlApi.deleteApi()

            emit(InstallStatus.Idle)
            withContext(dispatcherIo) {
                appDetailsRepository.setInstallStatus(id = id, newInstallStatus = InstallStatus.Idle)
            }
        }
            .catch{ error ->
                emit(InstallStatus.UninstallError(error))
                withContext(dispatcherIo) {
                    appDetailsRepository.setInstallStatus(id = id, newInstallStatus = InstallStatus.UninstallError(error))
                }
            }
}