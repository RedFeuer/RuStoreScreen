package com.example.rustorescreen.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rustorescreen.domain.domainModel.AppCategory
import com.example.rustorescreen.domain.domainModel.InstallStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDetailsDao {
    @Query("SELECT * FROM app_details WHERE id = :id") // получить все(*) из таблицы app_details по id
    fun getAppDetails(id: String): Flow<AppDetailsEntity?> // возвращаем поток, чтобы не делать в основном потоке и не замедлять приложение запросом в БД

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppDetails(appDetailsEntity: AppDetailsEntity)

    @Query("UPDATE app_details SET category = :newCategory WHERE id = :id")
    suspend fun updateAppCategory(id: String, newCategory: AppCategory)

    @Query("UPDATE app_details SET installStatus = :newInstallStatus WHERE id = :id")
    suspend fun updateInstallStatus(id: String, newInstallStatus: InstallStatus)
}