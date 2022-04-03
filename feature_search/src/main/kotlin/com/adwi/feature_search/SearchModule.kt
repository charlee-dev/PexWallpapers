package com.adwi.feature_search

import androidx.paging.ExperimentalPagingApi
import com.adwi.data.database.WallpaperDatabase
import com.adwi.data.network.PexService
import com.adwi.feature_search.data.SearchRepositoryImpl
import com.adwi.feature_search.domain.repository.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.ExperimentalCoroutinesApi

@Module
@InstallIn(ViewModelComponent::class)
object SearchModule {

    @ExperimentalPagingApi
    @ExperimentalCoroutinesApi
    @Provides
    @ViewModelScoped
    fun provideHomeRepository(
        service: PexService,
        database: WallpaperDatabase
    ): SearchRepository = SearchRepositoryImpl(service, database)
}