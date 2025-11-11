package com.example.rustorescreen.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AppListDao {
    @Query("SELECT * from app_details")
    fun getAppList(): Flow<List<AppDetailsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppList(apps: List<AppDetailsEntity>)
}