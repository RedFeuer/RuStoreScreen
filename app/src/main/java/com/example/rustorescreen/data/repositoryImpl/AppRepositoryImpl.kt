package com.example.rustorescreen.data.repositoryImpl

import com.example.rustorescreen.data.api.AppListAPI
import com.example.rustorescreen.data.mapper.AppMapper
import com.example.rustorescreen.domain.domainModel.App
import com.example.rustorescreen.domain.repositoryInterface.AppRepository

class AppRepositoryImpl : AppRepository {
    private val appListApi = AppListAPI()
    private val appMapper = AppMapper()

    override fun get() : List<App> {
        val dtoList = appListApi.getAppList() // Fetches the AppDto from the API
        val domainList = dtoList.map{ appMapper.toDomainModel(it) } // Maps the List<AppDto> to List<App>(Domain Model)
        return domainList
    }
}