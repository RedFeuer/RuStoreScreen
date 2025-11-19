package com.example.rustorescreen.data.repositoryImpl

import com.example.rustorescreen.data.api.AppListAPI
import com.example.rustorescreen.data.dto.AppDetailsDto
import com.example.rustorescreen.data.local.AppDetailsEntity
import com.example.rustorescreen.data.local.AppDetailsEntityMapper
import com.example.rustorescreen.data.local.AppListDao
import com.example.rustorescreen.data.mapper.AppDetailsMapper
import com.example.rustorescreen.domain.domainModel.AppDetails
import com.example.rustorescreen.domain.repositoryInterface.AppListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

// TODO: переписать документацию после внедрения базы данных
/**
 * Репозиторий для получения списка приложений.
 *
 * Инъекция в конструктор необходима для создания экземпляров через Dagger(Hilt).
 *
 * @property appListApi источник данных — сетевой API для получения списка приложений.
 * @property appDetailsMapper преобразует DTO в доменные модели `AppDetails`.
 */
class AppListRepositoryImpl @Inject constructor(
    private val appListApi: AppListAPI,
    private val dao: AppListDao,
    private val appDetailsMapper: AppDetailsMapper,
    private val appDetailsEntityMapper: AppDetailsEntityMapper,
) : AppListRepository {

    /**
     * Запрашивает список приложений у `AppListAPI` и маппит каждую DTO в доменную модель через `AppDetailsMapper`.
     *
     * Выполняется в корутине (`suspend`).
     *
     * @return список `AppDetails` — доменные модели приложений.
     */
    override fun get() : Flow<List<AppDetails>> {
        return dao.getAppList().map{ entities ->
            if (entities.isNotEmpty()) { /* получаем список приложений из базы данных, если таблица не пустая */
                entities.map{ entity->
                    appDetailsEntityMapper.toDomainModel(entity)
                }
            }
            else { /* получаем список приложений из сети(API) */
                val appsDto: List<AppDetailsDto> = appListApi.getAppList()
                val appsDomainModel: List<AppDetails> = appsDto.map{ appDto->
                    appDetailsMapper.toDomainModel(appDto)
                }

                /*сохранение(кэширование) в базу данных*/
                val appsEntity: List<AppDetailsEntity> = appsDomainModel.map { appDomainModel->
                    appDetailsEntityMapper.toEntity(appDomainModel)
                }
                withContext(Dispatchers.IO) {
                    dao.insertAppList(appsEntity)
                }

                appsDomainModel
            }
        }
    }
}