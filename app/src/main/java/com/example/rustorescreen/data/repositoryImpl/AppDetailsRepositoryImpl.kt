package com.example.rustorescreen.data.repositoryImpl

import com.example.rustorescreen.data.api.AppListAPI
import com.example.rustorescreen.data.dto.AppDetailsDto
import com.example.rustorescreen.data.local.AppDetailsDao
import com.example.rustorescreen.data.local.AppDetailsEntity
import com.example.rustorescreen.data.local.AppDetailsEntityMapper
import com.example.rustorescreen.data.mapper.AppDetailsMapper
import com.example.rustorescreen.domain.domainModel.AppDetails
import com.example.rustorescreen.domain.repositoryInterface.AppDetailsRepository
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
    override suspend fun getById(id: String): Flow<AppDetails> {
        return dao.getAppDetails(id).map { entity ->
            if (entity != null) { // получаем entity из базы данных
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
                withContext(context = Dispatchers.IO) {
                    dao.insertAppDetails(entity)
                }

                domainModel // возвращаем доменную модель, полученную из API
            }
        }
    }
}