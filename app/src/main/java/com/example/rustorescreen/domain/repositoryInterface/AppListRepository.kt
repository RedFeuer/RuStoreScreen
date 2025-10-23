package com.example.rustorescreen.domain.repositoryInterface

import com.example.rustorescreen.domain.domainModel.App

// Repository interface for fetching App data
interface AppListRepository {
    fun get(): List<App>
}