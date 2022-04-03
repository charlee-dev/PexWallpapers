package com.adwi.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.adwi.data.database.domain.DailyEntity
import com.adwi.data.database.domain.WallpaperEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDailyWallpapers(dailyEntity: List<DailyEntity>)

    @Query("SELECT * FROM daily_table INNER JOIN wallpaper_table ON id = wallpaperId")
    fun getAllDailyWallpapers(): Flow<List<WallpaperEntity>>

    @Query("DELETE FROM daily_table")
    suspend fun deleteAllDailyWallpapers()
}