package com.adwi.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.adwi.datasource.local.domain.SettingsEntity
import com.adwi.domain.Duration
import kotlinx.coroutines.flow.Flow

@Dao
interface SettingsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSettings(setting: SettingsEntity)

    @Query("SELECT * FROM settings_table")
    fun getSettings(): Flow<SettingsEntity>

    @Query("UPDATE settings_table SET lastQuery = :query")
    suspend fun updateLastQuery(query: String)

    @Query("UPDATE settings_table SET newWallpaperSet = :enabled")
    suspend fun updateNewWallpaperSet(enabled: Boolean)

    @Query("UPDATE settings_table SET wallpaperRecommendations = :enabled")
    suspend fun updateWallpaperRecommendations(enabled: Boolean)

    @Query("UPDATE settings_table SET autoChangeWallpaper = :enabled")
    suspend fun updateAutoChangeWallpaper(enabled: Boolean)

    @Query("UPDATE settings_table SET downloadOverWiFi = :enabled")
    suspend fun updateDownloadOverWiFi(enabled: Boolean)

    @Query("UPDATE settings_table SET durationSelected = :durationSelected")
    suspend fun updateChangePeriodType(durationSelected: Duration)

    @Query("UPDATE settings_table SET durationValue = :durationValue")
    suspend fun updateChangePeriodValue(durationValue: Float)

    @Query("UPDATE settings_table SET autoHome = :enabled")
    suspend fun updateAutoHome(enabled: Boolean)

    @Query("UPDATE settings_table SET autoLock = :enabled")
    suspend fun updateAutoLock(enabled: Boolean)
}