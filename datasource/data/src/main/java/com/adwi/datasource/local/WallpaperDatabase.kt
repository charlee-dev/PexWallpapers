package com.adwi.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.adwi.datasource.local.dao.*
import com.adwi.datasource.local.domain.*


@TypeConverters(Converters::class)
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
    exportSchema = false
)
abstract class WallpaperDatabase : RoomDatabase() {

    abstract fun dailyDao(): DailyDao
    abstract fun categoryDao(): CategoryDao
    abstract fun wallpaperDao(): WallpapersDao
    abstract fun searchDao(): SearchDao
    abstract fun settingsDao(): SettingsDao
}