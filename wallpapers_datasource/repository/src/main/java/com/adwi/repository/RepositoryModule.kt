package com.adwi.repository

import androidx.paging.ExperimentalPagingApi
import com.adwi.data.database.WallpaperDatabase
import com.adwi.data.network.PexService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @ExperimentalCoroutinesApi
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