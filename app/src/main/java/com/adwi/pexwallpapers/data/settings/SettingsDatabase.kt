package com.adwi.pexwallpapers.data.settings


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.adwi.pexwallpapers.data.settings.model.Converters
import com.adwi.pexwallpapers.data.settings.model.SettingsEntity

@TypeConverters(Converters::class)
@Database(
    entities = [SettingsEntity::class],
    version = 1,
    exportSchema = false
)
abstract class SettingsDatabase : RoomDatabase() {

    abstract fun settingsDao(): SettingsDao
}