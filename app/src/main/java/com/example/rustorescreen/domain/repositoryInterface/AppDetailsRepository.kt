package com.example.rustorescreen.domain.repositoryInterface

import com.example.rustorescreen.domain.domainModel.AppDetails

interface AppDetailsRepository {
    suspend fun get(): AppDetails
}