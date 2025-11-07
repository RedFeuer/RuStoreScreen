package com.example.rustorescreen.data.repositoryImpl

import com.example.rustorescreen.data.api.AppListAPI
import com.example.rustorescreen.data.dto.AppDetailsDto
import com.example.rustorescreen.data.mapper.AppDetailsMapper
import com.example.rustorescreen.domain.domainModel.AppDetails
import com.example.rustorescreen.domain.repositoryInterface.AppDetailsRepository
import javax.inject.Inject

/**
 * Реализация [AppDetailsRepository].
 *
 * Получает данные у [AppListAPI] и преобразует DTO в доменную модель с помощью [AppDetailsMapper].
 * Конструктор помечен `@Inject` для создания экземпляров через Dagger.
 */
class AppDetailsRepositoryImpl @Inject constructor(
    private val appListApi: AppListAPI,
    private val appDetailsMapper: AppDetailsMapper
): AppDetailsRepository {

    /**
     * Получить детали приложения по идентификатору.
     *
     * @param id идентификатор приложения
     * @return [AppDetails] доменная модель приложения
     * @throws NoSuchElementException если приложение с указанным id не найдено
     */
    override suspend fun getById(id: String): AppDetails {
        val dto: AppDetailsDto
        try {
            dto= appListApi.getAppById(id) // Fetches the AppDto from the API by id
        }
        catch(e: NoSuchElementException) {
            throw e // rethrow the exception if the app is not found
        }
        val domainModel: AppDetails = appDetailsMapper.toDomainModel(dto) // Maps the AppDto to AppDetails (Domain Model)
        return domainModel
    }
}