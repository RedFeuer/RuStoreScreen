package com.example.rustorescreen.data.local

import androidx.room.Database

@Database(
    entities = [AppDetailsEntity::class],
    version = 1,
)
abstract class AppDatabase {
    abstract fun appDetailsDao(): AppDetailsDao

    companion object {
        const val DATABASE_NAME = "app_list_database"
    }
}