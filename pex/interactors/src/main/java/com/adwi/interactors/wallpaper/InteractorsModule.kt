package com.adwi.interactors.wallpaper

import com.adwi.datasource.local.WallpaperDatabase
import com.adwi.datasource.network.PexService
import com.adwi.interactors.wallpaper.usecases.GetColors
import com.adwi.interactors.wallpaper.usecases.GetCurated
import com.adwi.interactors.wallpaper.usecases.GetDaily
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

    @Provides
    @Singleton
    fun provideWallpaperRepository(
        getCurated: GetCurated,
        getDaily: GetDaily,
        getColors: GetColors
    ): WallpaperInteractors {
        return WallpaperInteractors(getCurated, getDaily, getColors)
    }
}