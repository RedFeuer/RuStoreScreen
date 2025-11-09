package com.example.rustorescreen.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [AppDetailsEntity::class],
    version = 1,
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun appDetailsDao(): AppDetailsDao

    companion object {
        const val DATABASE_NAME = "app_list_database"
    }
}