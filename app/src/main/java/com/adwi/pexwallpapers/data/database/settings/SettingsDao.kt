package com.adwi.pexwallpapers.data.database.settings

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.adwi.pexwallpapers.data.database.settings.model.Settings
import kotlinx.coroutines.flow.Flow

@Dao
interface SettingsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSettings(setting: Settings)

    @Query("SELECT * FROM settings_table")
    fun getSettings(): Flow<Settings>

    // Notifications
    @Query("UPDATE settings_table SET pushNotifications = :enabled")
    suspend fun updatePushNotifications(enabled: Boolean)

    @Query("UPDATE settings_table SET newWallpaperSet = :enabled")
    suspend fun updateNewWallpaperSet(enabled: Boolean)

    @Query("UPDATE settings_table SET wallpaperRecommendations = :enabled")
    suspend fun updateWallpaperRecommendations(enabled: Boolean)

    // Automation
    @Query("UPDATE settings_table SET autoChangeWallpaper = :enabled")
    suspend fun updateAutoChangeWallpaper(enabled: Boolean)

    @Query("UPDATE settings_table SET autoHome = :enabled")
    suspend fun updateAutoHome(enabled: Boolean)

    @Query("UPDATE settings_table SET autoLock = :enabled")
    suspend fun updateAutoLock(enabled: Boolean)

    @Query("UPDATE settings_table SET minutes = :minutesValue")
    suspend fun updateMinutes(minutesValue: Int)

    @Query("UPDATE settings_table SET hours = :hoursValue")
    suspend fun updateHours(hoursValue: Int)

    @Query("UPDATE settings_table SET days = :daysValue")
    suspend fun updateDays(daysValue: Int)

    // Data saver
    @Query("UPDATE settings_table SET activateDataSaver = :enabled")
    suspend fun updateActivateDataSaver(enabled: Boolean)

    @Query("UPDATE settings_table SET downloadWallpapersOverWiFi = :enabled")
    suspend fun updateDownloadWallpapersOverWiFi(enabled: Boolean)

    @Query("UPDATE settings_table SET lowResMiniatures = :enabled")
    suspend fun updateDownloadMiniaturesLowQuality(enabled: Boolean)

    @Query("UPDATE settings_table SET autoChangeOverWiFi = :enabled")
    suspend fun updateAutoChangeOverWiFi(enabled: Boolean)
}