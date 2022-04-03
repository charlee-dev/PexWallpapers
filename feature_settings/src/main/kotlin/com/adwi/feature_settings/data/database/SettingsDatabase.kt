package com.adwi.feature_settings.data.database


import androidx.room.Database
import androidx.room.RoomDatabase
import com.adwi.feature_settings.data.database.model.Settings

@Database(
    entities = [Settings::class],
    version = 1,
    exportSchema = false
)
abstract class SettingsDatabase : RoomDatabase() {
    abstract fun settingsDao(): SettingsDao
}