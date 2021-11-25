package com.adwi.repository

import androidx.paging.ExperimentalPagingApi
import com.adwi.pexwallpapers.data.wallpapers.database.WallpaperDatabase
import com.adwi.pexwallpapers.data.wallpapers.network.PexService
import com.adwi.repository.wallpaper.WallpaperRepository
import com.adwi.repository.wallpaper.WallpaperRepositoryImpl
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

    @ExperimentalPagingApi
    @Provides
    @Singleton
    fun provideWallpaperRepository(
        database: com.adwi.pexwallpapers.data.wallpapers.database.WallpaperDatabase,
        service: com.adwi.pexwallpapers.data.wallpapers.network.PexService
    ): WallpaperRepository {
        return WallpaperRepositoryImpl(database, service)
    }
}