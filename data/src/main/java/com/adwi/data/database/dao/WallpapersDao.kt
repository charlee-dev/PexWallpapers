package com.adwi.data.database.dao

import androidx.room.*
import com.adwi.data.database.domain.CuratedEntity
import com.adwi.data.database.domain.WallpaperEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WallpapersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWallpapers(wallpapers: List<WallpaperEntity>)

    @Query("SELECT * FROM wallpaper_table")
    fun getAllWallpapers(): Flow<List<WallpaperEntity>>

    @Query("SELECT * FROM wallpaper_table WHERE id = :wallpaperId")
    fun getWallpaperById(wallpaperId: Int): Flow<WallpaperEntity>

    @Query("SELECT * FROM wallpaper_table WHERE categoryName = :categoryName")
    fun getWallpapersOfCategory(categoryName: String): Flow<List<WallpaperEntity>>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateWallpaper(wallpaper: WallpaperEntity)

    // Favorites
    @Query("UPDATE wallpaper_table SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateWallpaperIsFavorite(id: Int, isFavorite: Boolean)

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