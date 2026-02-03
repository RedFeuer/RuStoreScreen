package com.example.rustorescreen.data.repositoryImpl

import com.example.rustorescreen.data.api.ApkUrlApi
import com.example.rustorescreen.di.DispatcherIO
import com.example.rustorescreen.domain.domainModel.InstallStatus
import com.example.rustorescreen.domain.repositoryInterface.AppDetailsRepository
import com.example.rustorescreen.domain.repositoryInterface.DownloadingRepository
import com.example.rustorescreen.domain.repositoryInterface.InstallAppRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
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
    @DispatcherIO private val dispatcher: CoroutineDispatcher,
) : InstallAppRepository {
    override fun installApk(id: String): Flow<InstallStatus> =
        flow {
            /* устанавливаем в БД статус, что была попытка установки,
            * чтобы если установка будет прервана, то переустановить потом приложение */
            setHasInstallAttempts(id, true)

            apkUrlApi.lookingForApk()

            /* получаем URL для загрузки .apk файла из сети(API) */
            emitAndSaveStatus(id, InstallStatus.InstallPrepared)
            apkUrlApi.preparingApk()

            emitAndSaveStatus(id, InstallStatus.InstallStarted)
            apkUrlApi.getApk()

            /* отправляем все изменения при загрузке(от 1 до 100) .apk файла */
            emitAll(
                flow = downloadingRepository.processApk()
                    .map{ progress -> // преобразуем Flow<Int> -> Flow<InstallStatus>
                        if (progress % 20 == 0) {
                            setInstallStatus(id, installStatus = InstallStatus.Installing(progress)) // изменения в БД
                        }

                        InstallStatus.Installing(progress)
                    }
            )

            emitAndSaveStatus(id, InstallStatus.Installed)

            /* устанавливаем в БД статус, что попыток установки не было*/
            setHasInstallAttempts(id, false)
        }
            .catch { error ->
                emitAndSaveStatus(id, InstallStatus.InstallError(error))
            }

    override fun cancelApk(id: String): Flow<InstallStatus> =
        flow {
            emitAndSaveStatus(id, InstallStatus.Idle)
            apkUrlApi.collectGarbage() // очистка "мусора", который успел скачаться
        }
            .catch { error ->
                emitAndSaveStatus(id, InstallStatus.InstallError(error))
            }


    override fun uninstallApk(id: String): Flow<InstallStatus> =
        flow {
            emitAndSaveStatus(id, InstallStatus.UninstallPrepared)
            apkUrlApi.preparingApk()

            emitAndSaveStatus(id, InstallStatus.Uninstalling)
            apkUrlApi.deleteApi()

            emitAndSaveStatus(id, InstallStatus.Idle)
        }
            .catch{ error ->
                emitAndSaveStatus(id, InstallStatus.UninstallError(error))
            }

    /* extension-функция для FlowCollector<InstallStatus> чтобы вынести
    * повторяющиеся emit - setInstallStatus */
    private suspend fun FlowCollector<InstallStatus>.emitAndSaveStatus(
        id: String,
        installStatus: InstallStatus
    ) {
        emit(installStatus)
        setInstallStatus(id, installStatus)
    }

    private suspend fun setInstallStatus(id: String, installStatus: InstallStatus) {
        withContext(dispatcher) {
            appDetailsRepository.setInstallStatus(id, installStatus)
        }
    }

    private suspend fun setHasInstallAttempts(id: String, newHasInstallAttempts: Boolean) {
        withContext(dispatcher) {
            appDetailsRepository.setHasInstallAttempts(id = id, newHasInstallAttempts = newHasInstallAttempts)
        }
    }
}