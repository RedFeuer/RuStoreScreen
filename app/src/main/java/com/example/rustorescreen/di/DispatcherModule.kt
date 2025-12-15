package com.example.rustorescreen.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Qualifier
@Retention(value = AnnotationRetention.BINARY)
annotation class DispatcherIO

@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {

    @DispatcherIO
    @Provides
    fun provideDispatchersIo() : CoroutineDispatcher = Dispatchers.IO
}
