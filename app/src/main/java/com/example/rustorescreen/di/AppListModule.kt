package com.example.rustorescreen.di

import com.example.rustorescreen.data.repositoryImpl.AppListRepositoryImpl
import com.example.rustorescreen.domain.repositoryInterface.AppListRepository
import com.example.rustorescreen.domain.useCase.GetAppListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppListModule {

    @Provides
    @Singleton
    fun provideGetAppListUseCase(repository: AppListRepository): GetAppListUseCase {
        return GetAppListUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideAppListRepository(): AppListRepository {
        return AppListRepositoryImpl()
    }
}