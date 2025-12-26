package com.example.rustorescreen.data.repository

import com.example.rustorescreen.data.api.ApkUrlApi
import com.example.rustorescreen.data.repositoryImpl.InstallAppRepositoryImpl
import com.example.rustorescreen.domain.domainModel.InstallStatus
import com.example.rustorescreen.domain.repositoryInterface.AppDetailsRepository
import com.example.rustorescreen.domain.repositoryInterface.DownloadingRepository
import com.example.rustorescreen.domain.repositoryInterface.InstallAppRepository
import com.example.rustorescreen.domain.useCase.InstallAppUseCase
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test


class InstallAppRepositoryTest {
    private val appId: String = "fa2e31b8-1234-4cf7-9914-108a170a1b01"

    /* делаем mockk для репозиториев, чтобы не ругался на то, что возвращает Unit
    * методы из этих репозиториев используются в тестируемом методе*/
    private val appDetailsRepository: AppDetailsRepository = mockk(relaxed = true)
    private val apkUrlApi: ApkUrlApi = mockk(relaxed = true)
    private val downloadingRepository: DownloadingRepository = mockk()

    /* ЗАПУТАЛСЯ */
    @Test
    fun `install without error EXPECT installed`() = runTest {
        /* тестовый Dispatcher для подмены Dispatchers.IO */
        val testDispatcher = StandardTestDispatcher(testScheduler)
        /* делаем mockk самого репозитория, метод в котором тестируем */
        val installAppRepositoryMocked: InstallAppRepository = mockk()
        /* будем проверять прямо весь прогресс */
        every {installAppRepositoryMocked.installApk(id = appId)} returns
                flowOf(InstallStatus.InstallPrepared, InstallStatus.InstallStarted,
                    InstallStatus.Installing(0), InstallStatus.Installing(20),
                    InstallStatus.Installing(40), InstallStatus.Installing(60),
                    InstallStatus.Installing(80), InstallStatus.Installing(100),
                    InstallStatus.Installed)


        /* инстанцируем настоящий репозиторий через конструктор */
        val installAppRepository: InstallAppRepository = InstallAppRepositoryImpl(
            appDetailsRepository = appDetailsRepository,
            apkUrlApi = apkUrlApi,
            downloadingRepository = downloadingRepository,
            dispatcherIo = testDispatcher, // тестовый Dispatcher
        )
        /* триггерим тестируемую логику */
        val installAppUseCase: InstallAppUseCase = InstallAppUseCase(installAppRepository)

        val result: List<InstallStatus> = installAppUseCase.invoke(id = appId).toList()

        assertEquals(9, result.size)
        assertEquals(InstallStatus.InstallPrepared, result[0])
        assertEquals(InstallStatus.InstallStarted, result[1])
        assertEquals(InstallStatus.Installing(0), result[2])
        assertEquals(InstallStatus.Installing(20), result[3])
        assertEquals(InstallStatus.Installing(40), result[4])
        assertEquals(InstallStatus.Installing(60), result[5])
        assertEquals(InstallStatus.Installing(80), result[6])
        assertEquals(InstallStatus.Installing(100), result[7])
        assertEquals(InstallStatus.Installed, result[8])
    }
}