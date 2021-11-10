package com.adwi.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.adwi.datasource.local.dao.SearchDao
import com.adwi.datasource.local.dao.SettingsDao
import com.adwi.datasource.local.dao.WallpapersDao
import com.adwi.datasource.local.domain.*

@Database(
    entities = [
        WallpaperEntity::class,
        CuratedEntity::class,
        SearchResultEntity::class,
        SearchQueryRemoteKey::class,
        SettingsEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class WallpaperDatabase : RoomDatabase() {

    abstract fun wallpaperDao(): WallpapersDao
    abstract fun searchDao(): SearchDao
    abstract fun settingsDao(): SettingsDao
}