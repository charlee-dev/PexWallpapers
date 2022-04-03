package com.adwi.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.adwi.data.database.dao.DailyDao
import com.adwi.data.database.dao.SearchDao
import com.adwi.data.database.dao.WallpapersDao
import com.adwi.data.database.domain.*


@Database(
    entities = [
        WallpaperEntity::class,
        CuratedEntity::class,
        SearchResultEntity::class,
        SearchQueryRemoteKey::class,
        DailyEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class WallpaperDatabase : RoomDatabase() {

    abstract fun dailyDao(): DailyDao
    abstract fun wallpaperDao(): WallpapersDao
    abstract fun searchDao(): SearchDao
}