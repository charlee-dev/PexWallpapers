package com.adwi.datasource.local.dao

import androidx.room.*
import com.adwi.datasource.local.domain.CuratedEntity
import com.adwi.datasource.local.domain.WallpaperEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WallpapersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWallpapers(wallpapers: List<WallpaperEntity>)

    @Query("SELECT * FROM wallpaper_table")
    suspend fun getAllWallpapers(): List<WallpaperEntity>

    @Query("SELECT * FROM wallpaper_table WHERE id = :wallpaperId")
    fun getWallpaperById(wallpaperId: Int): Flow<WallpaperEntity>

    @Query("SELECT * FROM wallpaper_table WHERE categoryName = :categoryName")
    fun getWallpapersOfCategory(categoryName: String): Flow<List<WallpaperEntity>>

    @Update
    suspend fun updateWallpaper(wallpaper: WallpaperEntity)

    // Favorites

    @Query("SELECT * FROM wallpaper_table WHERE isFavorite = 1")
    fun getAllFavorites(): Flow<List<WallpaperEntity>>

    @Query("UPDATE wallpaper_table SET isFavorite = 0")
    suspend fun resetAllFavorites()

    @Query("DELETE FROM wallpaper_table WHERE updatedAt < :timestampInMillis AND isFavorite = 0")
    suspend fun deleteNonFavoriteWallpapersOlderThan(timestampInMillis: Long)

    // Curated
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCuratedWallpapers(wallpapers: List<CuratedEntity>)

    @Query("SELECT * FROM curated_wallpapers INNER JOIN wallpaper_table ON id = wallpaperId")
    fun getAllCuratedWallpapers(): Flow<List<WallpaperEntity>>

    @Query("DELETE FROM curated_wallpapers")
    suspend fun deleteAllCuratedWallpapers()
}