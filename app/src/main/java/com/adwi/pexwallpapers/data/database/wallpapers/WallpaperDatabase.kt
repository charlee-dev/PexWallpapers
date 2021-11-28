package com.adwi.pexwallpapers.data.database.wallpapers

import androidx.room.Database
import androidx.room.RoomDatabase
import com.adwi.pexwallpapers.data.database.wallpapers.dao.CategoryDao
import com.adwi.pexwallpapers.data.database.wallpapers.dao.DailyDao
import com.adwi.pexwallpapers.data.database.wallpapers.dao.SearchDao
import com.adwi.pexwallpapers.data.database.wallpapers.dao.WallpapersDao
import com.adwi.pexwallpapers.data.database.wallpapers.domain.*


@Database(
    entities = [
        WallpaperEntity::class,
        CuratedEntity::class,
        SearchResultEntity::class,
        SearchQueryRemoteKey::class,
        DailyEntity::class,
        ColorCategoryEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class WallpaperDatabase : RoomDatabase() {

    abstract fun dailyDao(): DailyDao
    abstract fun categoryDao(): CategoryDao
    abstract fun wallpaperDao(): WallpapersDao
    abstract fun searchDao(): SearchDao
}