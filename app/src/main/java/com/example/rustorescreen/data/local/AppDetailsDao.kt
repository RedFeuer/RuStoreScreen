package com.example.rustorescreen.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDetailsDao {
    @Query("SELECT * from app_list_database WHERE id = :id") // получить из БД app_list_database по id
    fun getAppDetails(id: String): Flow<AppDetailsEntity?> // возвращаем поток, чтобы не делать в основном потоке и не замедлять приложение

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppDetails(appDetailsEntity: AppDetailsEntity)
}