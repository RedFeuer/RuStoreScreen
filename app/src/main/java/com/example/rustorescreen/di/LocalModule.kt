package com.example.rustorescreen.di

import android.app.Application
import androidx.room.Room
import com.example.rustorescreen.data.local.AppDatabase
import com.example.rustorescreen.data.local.AppDetailsDao
import com.example.rustorescreen.data.local.AppDetailsEntityMapper
import com.example.rustorescreen.data.local.AppListDao
import com.example.rustorescreen.data.local.MIGRATION_2_3
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
        )
            .addMigrations(MIGRATION_2_3) // добавлена колонка installStatus
            .build()
    }

    @Provides
    @Singleton
    fun provideAppDetailsDao(database: AppDatabase): AppDetailsDao {
        return database.appDetailsDao()
    }

    @Provides
    @Singleton
    fun provideAppListDao(database: AppDatabase): AppListDao {
        return database.appListDao()
    }

    @Provides
    @Singleton
    fun provideAppDetailsEntityMapper(): AppDetailsEntityMapper {
        return AppDetailsEntityMapper()
    }
}