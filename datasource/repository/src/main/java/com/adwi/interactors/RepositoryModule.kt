package com.adwi.interactors

import androidx.paging.ExperimentalPagingApi
import com.adwi.datasource.local.WallpaperDatabase
import com.adwi.datasource.local.dao.SettingsDao
import com.adwi.datasource.network.PexService
import com.adwi.interactors.settings.SettingsRepository
import com.adwi.interactors.settings.SettingsRepositoryImpl
import com.adwi.interactors.wallpaper.WallpaperRepository
import com.adwi.interactors.wallpaper.WallpaperRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideSettingsRepository(
        settingsDao: SettingsDao
    ): SettingsRepository = SettingsRepositoryImpl(settingsDao)

    @ExperimentalPagingApi
    @Provides
    @Singleton
    fun provideWallpaperRepository(
        database: WallpaperDatabase,
        service: PexService
    ): WallpaperRepository {
        return WallpaperRepositoryImpl(database, service)
    }
}