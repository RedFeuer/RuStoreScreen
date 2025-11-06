package com.example.rustorescreen.di

import com.example.rustorescreen.data.repositoryImpl.AppListRepositoryImpl
import com.example.rustorescreen.domain.repositoryInterface.AppListRepository
import com.example.rustorescreen.domain.useCase.GetAppListUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppListModule {

    @Binds // Биндим реализацию репозитория к его интерфейсу
    @Singleton
    abstract fun bindAppListRepository(impl: AppListRepositoryImpl): AppListRepository

    companion object {
        @Provides
        @Singleton
        fun provideGetAppListUseCase(repository: AppListRepository): GetAppListUseCase {
            return GetAppListUseCase(repository)
        }
    }
}