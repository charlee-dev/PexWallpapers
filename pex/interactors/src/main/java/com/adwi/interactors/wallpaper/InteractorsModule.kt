package com.adwi.interactors.wallpaper

import com.adwi.core.util.Logger
import com.adwi.datasource.local.WallpaperDatabase
import com.adwi.datasource.network.PexService
import com.adwi.interactors.wallpaper.usecases.WallpaperRepository
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
    fun provideWallpapersUseCase(
        service: PexService,
        database: WallpaperDatabase,
        logger: Logger
    ): WallpaperRepository = WallpaperRepository(service, database)


    @Provides
    @Singleton
    fun provideWallpaperRepository(
        wallpaperRepository: WallpaperRepository
    ): WallpaperInteractors {
        return WallpaperInteractors(wallpaperRepository)
    }
}