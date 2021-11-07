package com.adwi.data.cache.wallpaper

import com.adwi.data.cache.CuratedEntity
import com.adwi.domain.Wallpaper
import kotlinx.coroutines.flow.Flow

interface WallpaperCache {

    // Wallpaper

    fun getAllWallpapers(): Flow<List<Wallpaper>>

    suspend fun insertWallpaper(wallpaper: Wallpaper)

    suspend fun getWallpaperById(id: Long): Wallpaper

    suspend fun getWallpapersOfCategory(name: String): List<Wallpaper>

    suspend fun deleteWallpaper(id: Long)

    suspend fun updateWallpaperIsFavorite(wallpaper: Wallpaper)

    // Curated

    suspend fun insertCurated(curatedEntity: CuratedEntity)

    suspend fun getAllCurated(): Flow<List<Wallpaper>>

    suspend fun deleteAllCuratedWallpapers()
}