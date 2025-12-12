package com.example.rustorescreen.data.repositoryImpl

import com.example.rustorescreen.data.api.AppListAPI
import com.example.rustorescreen.data.dto.AppDetailsDto
import com.example.rustorescreen.data.local.AppDetailsDao
import com.example.rustorescreen.data.local.AppDetailsEntity
import com.example.rustorescreen.data.local.AppDetailsEntityMapper
import com.example.rustorescreen.data.mapper.AppDetailsMapper
import com.example.rustorescreen.domain.domainModel.AppCategory
import com.example.rustorescreen.domain.domainModel.AppDetails
import com.example.rustorescreen.domain.domainModel.InstallStatus
import com.example.rustorescreen.domain.repositoryInterface.AppDetailsRepository
import com.example.rustorescreen.util.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

/* TODO: ПЕРЕПИСАТЬ ДОКУМЕНТАЦИЮ */
/**
 * Реализация [AppDetailsRepository].
 *
 * Получает данные у [AppListAPI] и преобразует DTO в доменную модель с помощью [AppDetailsMapper].
 * Конструктор помечен `@Inject` для создания экземпляров через Dagger.
 */
class AppDetailsRepositoryImpl @Inject constructor(
    private val appListApi: AppListAPI,
    private val dao: AppDetailsDao,
    private val appDetailsMapper: AppDetailsMapper,
    private val appDetailsEntityMapper: AppDetailsEntityMapper,

    private val logger: Logger,
): AppDetailsRepository {

    /**
     * Метод возвращает `Flow<AppDetails>`, чтобы доступ к сети/базе данных и преобразование DTO
     * выполнялись асинхронно и не блокировали главный поток. Коллекционировать Flow следует
     * на `Dispatchers.IO`, чтобы избежать долгих операций в UI-потоке.
     *
     * @param id идентификатор приложения
     * @return Flow<AppDetails> доменная модель приложения
     * @throws NoSuchElementException если приложение с указанным id не найдено
     */
    override fun getById(id: String): Flow<AppDetails> {
        return dao.getAppDetails(id).map { entity ->
            if (entity != null) { // получаем entity из базы данных
                /* в случае проблем с установкой - записать в Logcat что не так */
                val message: String = entity.installStatus.toString()
                logger.e(message = "getAppDetails", throwable = Throwable(message))

                appDetailsEntityMapper.toDomainModel(entity)
            }
            else { // получаем DTO из API
                val dto: AppDetailsDto
                try {
                    dto = appListApi.getAppById(id)// получаем
                }
                catch(e: NoSuchElementException) {
                    throw e // ловим исключение, если такого id нет
                }
                val domainModel: AppDetails = appDetailsMapper.toDomainModel(dto)

                /* кэшируем в базу данных, если такого приложения не было */
                val entity: AppDetailsEntity = appDetailsEntityMapper.toEntity(domainModel)
                /* переключаем Dispatchers.Default из ViewModel на Dispatchers.IO, чтобы обратиться к БД */
                withContext(context = Dispatchers.IO) {
                    dao.insertAppDetails(entity)
                }

                domainModel // возвращаем доменную модель, полученную из API
            }
        }
    }

    override suspend fun setAppCategory(id: String, newCategory: AppCategory) {
        dao.updateAppCategory(id = id, newCategory = newCategory)
    }

    override suspend fun setInstallStatus(id: String, newInstallStatus: InstallStatus) {
        dao.updateInstallStatus(id = id, newInstallStatus = newInstallStatus)
    }

    override suspend fun setHasInstallAttempts(id: String, newHasInstallAttempts: Boolean) {
        dao.updateHasInstallAttempts(id = id, newHasInstallAttempts = newHasInstallAttempts)
    }
}