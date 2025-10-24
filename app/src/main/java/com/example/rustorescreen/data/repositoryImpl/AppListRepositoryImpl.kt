package com.example.rustorescreen.data.repositoryImpl

import com.example.rustorescreen.data.api.AppListAPI
import com.example.rustorescreen.data.dto.AppDto
import com.example.rustorescreen.data.mapper.AppMapper
import com.example.rustorescreen.domain.domainModel.AppDetails
import com.example.rustorescreen.domain.repositoryInterface.AppListRepository

class AppListRepositoryImpl : AppListRepository {
    private val appListApi = AppListAPI()
    private val appMapper = AppMapper()

    override fun get() : List<AppDetails> {
        val dtoList: List<AppDto> = appListApi.getAppList() // Fetches the List of AppDto from the API
        val domainList: List<AppDetails> = dtoList.map{ dto -> appMapper.toDomainModel(dto) } // Maps the List<AppDto> to List<App>(Domain Model)
        return domainList
    }
}