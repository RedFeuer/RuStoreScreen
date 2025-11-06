package com.example.rustorescreen.data.repositoryImpl

import com.example.rustorescreen.data.api.AppListAPI
import com.example.rustorescreen.data.dto.AppDetailsDto
import com.example.rustorescreen.data.mapper.AppDetailsMapper
import com.example.rustorescreen.domain.domainModel.AppDetails
import com.example.rustorescreen.domain.repositoryInterface.AppDetailsRepository
import javax.inject.Inject

// инъекция в конструктор для того, чтобы Dagger мог создавать экземпляры этого класса
class AppDetailsRepositoryImpl @Inject constructor(
    private val appListApi: AppListAPI,
    private val appDetailsMapper: AppDetailsMapper
): AppDetailsRepository {

    /* converts dto -> domainModel or throws exception,
    * if there is no such app in AppList */
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