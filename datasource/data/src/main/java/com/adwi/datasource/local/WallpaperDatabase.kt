package com.adwi.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.adwi.datasource.local.dao.*
import com.adwi.datasource.local.domain.*

@Database(
    entities = [
        WallpaperEntity::class,
        CuratedEntity::class,
        SearchResultEntity::class,
        SearchQueryRemoteKey::class,
        SettingsEntity::class,
        DailyEntity::class,
        ColorCategoryEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class WallpaperDatabase : RoomDatabase() {

    abstract fun dailyDao(): DailyDao
    abstract fun categoryDao(): CategoryDao
    abstract fun wallpaperDao(): WallpapersDao
    abstract fun searchDao(): SearchDao
    abstract fun settingsDao(): SettingsDao
}