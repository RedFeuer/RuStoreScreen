package com.example.rustorescreen.di

import com.example.rustorescreen.data.api.AppListAPI
import com.example.rustorescreen.data.mapper.AppDetailsMapper
import com.example.rustorescreen.data.repositoryImpl.AppDetailsRepositoryImpl
import com.example.rustorescreen.domain.repositoryInterface.AppDetailsRepository
import com.example.rustorescreen.domain.useCase.GetAppDetailsUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
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

        @Provides
        @Singleton
        fun provideAppMapper(): AppDetailsMapper {
            return AppDetailsMapper()
        }

        // Предоставляем Retrofit для AppDetailsAPI
        @Provides
        @Singleton
        fun provideRetrofitAppDetailsAPI(): Retrofit {
            val contentType = "application/json".toMediaType()
            val json = Json { ignoreUnknownKeys = true }
            return Retrofit.Builder()
                .baseUrl("http://185.103.109.134/") // Базовый URL для API
                .addConverterFactory(json.asConverterFactory(contentType))
                .build()
        }

        @Provides
        @Singleton
        fun provideAppListApi(retrofit: Retrofit): AppListAPI {
            return retrofit.create(AppListAPI::class.java)
        }
    }
}