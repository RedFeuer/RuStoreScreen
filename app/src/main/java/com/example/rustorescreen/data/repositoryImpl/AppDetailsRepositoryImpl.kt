package com.example.rustorescreen.data.repositoryImpl

import com.example.rustorescreen.data.api.AppListAPI
import com.example.rustorescreen.data.dto.AppDto
import com.example.rustorescreen.data.mapper.AppMapper
import com.example.rustorescreen.domain.domainModel.AppDetails
import com.example.rustorescreen.domain.repositoryInterface.AppDetailsRepository
import javax.inject.Inject

// инъекция в конструктор для того, чтобы Dagger мог создавать экземпляры этого класса
class AppDetailsRepositoryImpl @Inject constructor(): AppDetailsRepository {
    private val appListApi = AppListAPI()
    private val appMapper = AppMapper()

    /* converts dto -> domainModel or throws exception,
    * if there is no such app in AppList */
    override suspend fun getById(id: Int): AppDetails {
        val dto: AppDto
        try {
            dto= appListApi.getById(id) // Fetches the AppDto from the API by id
        }
        catch(e: NoSuchElementException) {
            throw e // rethrow the exception if the app is not found
        }
        val domainModel: AppDetails = appMapper.toDomainModel(dto) // Maps the AppDto to AppDetails (Domain Model)
        return domainModel
    }
}