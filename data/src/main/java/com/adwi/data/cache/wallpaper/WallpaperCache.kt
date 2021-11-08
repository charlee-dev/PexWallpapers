package com.adwi.data.cache.wallpaper

import com.adwi.base.util.Constants
import com.adwi.data.cache.CuratedEntity
import com.adwi.data.cache.WallpaperDb
import com.adwi.domain.Wallpaper
import com.squareup.sqldelight.db.SqlDriver

interface WallpaperCache {

    // Wallpaper

    suspend fun getAllWallpapers(): List<Wallpaper>

    suspend fun insertWallpaper(wallpaper: Wallpaper)

    suspend fun getWallpaperById(id: Long): Wallpaper

    suspend fun getWallpapersOfCategory(name: String): List<Wallpaper>

    suspend fun deleteWallpaper(id: Long)

    suspend fun updateWallpaperIsFavorite(wallpaper: Wallpaper)

    // Curated

    suspend fun insertCurated(curatedEntity: CuratedEntity)

    suspend fun getAllCurated(): List<Wallpaper>

    suspend fun deleteAllCuratedWallpapers()

    companion object Factory {
        fun build(sqlDriver: SqlDriver): WallpaperCache {
            return WallpaperCacheImpl(WallpaperDb(sqlDriver))
        }

        val schema: SqlDriver.Schema = WallpaperDb.Schema

        val dbName: String = Constants.WALLPAPER_DATABASE
    }
}