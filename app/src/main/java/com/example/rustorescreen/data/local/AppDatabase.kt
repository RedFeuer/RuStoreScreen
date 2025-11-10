package com.example.rustorescreen.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [AppDetailsEntity::class],
    version = 1,
)
@TypeConverters(CategoryConverter::class, ScreenshotsListConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun appDetailsDao(): AppDetailsDao

    companion object {
        const val DATABASE_NAME = "app_database"
    }
}