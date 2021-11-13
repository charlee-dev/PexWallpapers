package com.adwi.interactors

import androidx.paging.ExperimentalPagingApi
import com.adwi.datasource.local.WallpaperDatabase
import com.adwi.datasource.network.PexService
import com.adwi.interactors.settings.SettingsInteractors
import com.adwi.interactors.settings.usecases.GetSettings
import com.adwi.interactors.wallpaper.WallpaperInteractors
import com.adwi.interactors.wallpaper.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
object InteractorsModule {

    @Provides
    @Singleton
    fun provideGetWallpaper(
        database: WallpaperDatabase
    ): GetWallpaper = GetWallpaper(database)

    @Provides
    @Singleton
    fun provideGetCurated(
        service: PexService,
        database: WallpaperDatabase
    ): GetCurated = GetCurated(service, database)

    @Provides
    @Singleton
    fun provideGetDaily(
        service: PexService,
        database: WallpaperDatabase
    ): GetDaily = GetDaily(service, database)

    @Provides
    @Singleton
    fun provideGetColors(
        service: PexService,
        database: WallpaperDatabase
    ): GetColors = GetColors(service, database)

    @ExperimentalPagingApi
    @Provides
    @Singleton
    fun provideGetSearch(
        service: PexService,
        database: WallpaperDatabase
    ): GetSearch = GetSearch(service, database)

    @Provides
    @Singleton
    fun provideGetSettings(
        database: WallpaperDatabase
    ): GetSettings = GetSettings(database)

    @ExperimentalPagingApi
    @Provides
    @Singleton
    fun provideWallpaperInteractors(
        getWallpaper: GetWallpaper,
        getCurated: GetCurated,
        getDaily: GetDaily,
        getColors: GetColors,
        getSearch: GetSearch
    ): WallpaperInteractors {
        return WallpaperInteractors(getWallpaper, getCurated, getDaily, getColors, getSearch)
    }

    @Provides
    @Singleton
    fun provideSettingsInteractors(
        getSettings: GetSettings
    ): SettingsInteractors {
        return SettingsInteractors(getSettings)
    }
}