package com.example.rustorescreen.data.repositoryImpl

import com.example.rustorescreen.data.api.AppListAPI
import com.example.rustorescreen.data.dto.AppDetailsDto
import com.example.rustorescreen.data.mapper.AppDetailsMapper
import com.example.rustorescreen.domain.domainModel.AppDetails
import com.example.rustorescreen.domain.repositoryInterface.AppListRepository
import javax.inject.Inject


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
    private val appDetailsMapper: AppDetailsMapper
) : AppListRepository {

    /**
     * Запрашивает список приложений у `AppListAPI` и маппит каждую DTO в доменную модель через `AppDetailsMapper`.
     *
     * Выполняется в корутине (`suspend`).
     *
     * @return список `AppDetails` — доменные модели приложений.
     */
    override suspend fun get() : List<AppDetails> {
        val dtoList: List<AppDetailsDto> = appListApi.getAppList() // Fetches the List of AppDto from the API
        val domainList: List<AppDetails> = dtoList.map{ dto -> appDetailsMapper.toDomainModel(dto) } // Maps the List<AppDto> to List<App>(Domain Model)
        return domainList
    }
}