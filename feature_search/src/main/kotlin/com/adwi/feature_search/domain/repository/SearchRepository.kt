package com.adwi.feature_search.domain.repository

import androidx.paging.PagingData
import com.adwi.data.database.domain.WallpaperEntity
import com.adwi.pexwallpapers.domain.model.Wallpaper
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    fun getSearch(query: String): Flow<PagingData<WallpaperEntity>>

    suspend fun updateWallpaper(wallpaper: Wallpaper)
}