package com.adwi.feature_settings

import android.content.Context
import androidx.room.Room
import com.adwi.feature_settings.data.database.Constants.SETTINGS_DATABASE
import com.adwi.feature_settings.data.database.SettingsDao
import com.adwi.feature_settings.data.database.SettingsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SettingsModule {

    @Provides
    @Singleton
    fun provideSettingsDatabase(@ApplicationContext context: Context): SettingsDatabase =
        Room
            .databaseBuilder(
                context,
                SettingsDatabase::class.java,
                SETTINGS_DATABASE
            )
            .createFromAsset("database/settings.db")
            .build()

    @Provides
    @Singleton
    fun provideSettingsDao(settingsDatabase: SettingsDatabase): SettingsDao =
        settingsDatabase.settingsDao()
}