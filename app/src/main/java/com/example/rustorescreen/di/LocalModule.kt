package com.example.rustorescreen.di

import android.app.Application
import androidx.room.Room
import com.example.rustorescreen.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {
    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            context = app,
            klass = AppDatabase::class.java,
            name = AppDatabase.DATABASE_NAME
        ).build()
    }
}