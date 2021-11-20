package com.adwi.datasource

import android.content.Context
import androidx.room.Room
import com.adwi.common.util.Constants
import com.adwi.datasource.local.WallpaperDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context) =
        Room
            .databaseBuilder(
                context,
                WallpaperDatabase::class.java,
                Constants.WALLPAPER_DATABASE
            )
            .createFromAsset("database/settings.db")
            .build()

    @Provides
    @Singleton
    fun provideWallpaperDao(appDatabase: WallpaperDatabase) =
        appDatabase.wallpaperDao()

    @Provides
    @Singleton
    fun provideDailyDao(appDatabase: WallpaperDatabase) =
        appDatabase.dailyDao()

    @Provides
    @Singleton
    fun provideCategoryDao(appDatabase: WallpaperDatabase) =
        appDatabase.categoryDao()

    @Provides
    @Singleton
    fun provideSearchDao(appDatabase: WallpaperDatabase) =
        appDatabase.searchDao()

    @Provides
    @Singleton
    fun provideSettingsDao(appDatabase: WallpaperDatabase) =
        appDatabase.settingsDao()
}