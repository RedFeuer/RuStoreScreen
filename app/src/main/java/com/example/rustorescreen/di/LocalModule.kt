package com.example.rustorescreen.di

import android.app.Application
import androidx.room.Room
import com.example.rustorescreen.data.local.AppDatabase
import com.example.rustorescreen.data.local.AppDetailsDao
import com.example.rustorescreen.data.local.AppDetailsEntityMapper
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

    @Provides
    @Singleton
    fun provideAppDetailsDao(database: AppDatabase): AppDetailsDao {
        return database.appDetailsDao()
    }

    @Provides
    @Singleton
    fun provideAppDetailsEntityMapper(): AppDetailsEntityMapper {
        return AppDetailsEntityMapper()
    }
}