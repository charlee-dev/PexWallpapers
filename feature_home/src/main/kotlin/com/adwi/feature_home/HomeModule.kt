package com.adwi.feature_home

import com.adwi.data.database.WallpaperDatabase
import com.adwi.data.network.PexService
import com.adwi.feature_home.repository.HomeRepositoryImpl
import com.adwi.feature_home.repository.HomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.ExperimentalCoroutinesApi

@Module
@InstallIn(ViewModelComponent::class)
object HomeModule {

    @ExperimentalCoroutinesApi
    @Provides
    @ViewModelScoped
    fun provideHomeRepository(
        service: PexService,
        database: WallpaperDatabase
    ): HomeRepository = HomeRepositoryImpl(service, database)
}