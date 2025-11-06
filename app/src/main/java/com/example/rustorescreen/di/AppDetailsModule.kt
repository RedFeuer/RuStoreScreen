package com.example.rustorescreen.di

import com.example.rustorescreen.data.repositoryImpl.AppDetailsRepositoryImpl
import com.example.rustorescreen.domain.repositoryInterface.AppDetailsRepository
import com.example.rustorescreen.domain.useCase.GetAppDetailsUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppDetailsModule {


    @Binds // Биндим реализацию репозитория к его интерфейсу
    @Singleton
    abstract fun bindAppDetailsRepository(impl: AppDetailsRepositoryImpl): AppDetailsRepository

    companion object {
        @Provides
        @Singleton
        fun provideGetAppDetailsUseCase(repository: AppDetailsRepository): GetAppDetailsUseCase {
            return GetAppDetailsUseCase(repository)
        }
    }
}