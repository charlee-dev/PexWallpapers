package com.adwi.pexwallpapers.data.settings

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.adwi.datasource_settings.domain.Duration
import com.adwi.pexwallpapers.data.settings.model.SettingsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SettingsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSettings(setting: SettingsEntity)

    @Query("SELECT * FROM settings_table")
    fun getSettings(): Flow<SettingsEntity>

    @Query("UPDATE settings_table SET lastQuery = :query")
    suspend fun updateLastQuery(query: String)

    @Query("UPDATE settings_table SET pushNotifications = :enabled")
    suspend fun updatePushNotifications(enabled: Boolean)

    @Query("UPDATE settings_table SET newWallpaperSet = :enabled")
    suspend fun updateNewWallpaperSet(enabled: Boolean)

    @Query("UPDATE settings_table SET wallpaperRecommendations = :enabled")
    suspend fun updateWallpaperRecommendations(enabled: Boolean)

    @Query("UPDATE settings_table SET autoChangeWallpaper = :enabled")
    suspend fun updateAutoChangeWallpaper(enabled: Boolean)

    @Query("UPDATE settings_table SET downloadOverWiFi = :enabled")
    suspend fun updateDownloadOverWiFi(enabled: Boolean)

    @Query("UPDATE settings_table SET durationSelected = :durationSelected")
    suspend fun updateChangeDurationSelected(durationSelected: Duration)

    @Query("UPDATE settings_table SET durationValue = :durationValue")
    suspend fun updateChangeDurationValue(durationValue: Float)

    @Query("UPDATE settings_table SET autoHome = :enabled")
    suspend fun updateAutoHome(enabled: Boolean)

    @Query("UPDATE settings_table SET autoLock = :enabled")
    suspend fun updateAutoLock(enabled: Boolean)
}