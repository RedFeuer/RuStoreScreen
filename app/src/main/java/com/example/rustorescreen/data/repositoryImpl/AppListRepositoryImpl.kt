package com.example.rustorescreen.data.repositoryImpl

import com.example.rustorescreen.data.api.AppListAPI
import com.example.rustorescreen.data.dto.AppDetailsDto
import com.example.rustorescreen.data.mapper.AppDetailsMapper
import com.example.rustorescreen.domain.domainModel.AppDetails
import com.example.rustorescreen.domain.repositoryInterface.AppListRepository
import javax.inject.Inject


// инъекция в конструктор для того, чтобы Dagger мог создавать экземпляры этого класса
class AppListRepositoryImpl @Inject constructor(
    private val appListApi: AppListAPI,
    private val appDetailsMapper: AppDetailsMapper
) : AppListRepository {

    override suspend fun get() : List<AppDetails> {
        val dtoList: List<AppDetailsDto> = appListApi.getAppList() // Fetches the List of AppDto from the API
        val domainList: List<AppDetails> = dtoList.map{ dto -> appDetailsMapper.toDomainModel(dto) } // Maps the List<AppDto> to List<App>(Domain Model)
        return domainList
    }
}