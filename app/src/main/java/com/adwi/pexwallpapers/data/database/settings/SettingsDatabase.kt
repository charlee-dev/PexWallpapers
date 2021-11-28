package com.adwi.pexwallpapers.data.database.settings


import androidx.room.Database
import androidx.room.RoomDatabase
import com.adwi.pexwallpapers.data.database.settings.model.Settings

@Database(
    entities = [Settings::class],
    version = 1,
    exportSchema = false
)
abstract class SettingsDatabase : RoomDatabase() {

    abstract fun settingsDao(): SettingsDao
}