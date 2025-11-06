package com.example.rustorescreen.domain.repositoryInterface

import com.example.rustorescreen.domain.domainModel.AppDetails

// Repository interface for fetching App data
interface AppListRepository {
    suspend fun get(): List<AppDetails>
}