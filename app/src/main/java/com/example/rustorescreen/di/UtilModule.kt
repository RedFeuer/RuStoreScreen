package com.example.rustorescreen.di

import com.example.rustorescreen.util.Logger
import com.example.rustorescreen.util.LoggerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class UtilModule {
    @Binds
    @Singleton
    abstract fun bindLogger(impl: LoggerImpl): Logger
}