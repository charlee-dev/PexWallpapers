package com.adwi.feature_home.repository

import com.adwi.core.DataState
import com.adwi.data.util.Constants
import com.adwi.pexwallpapers.domain.model.Wallpaper
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    fun getCurated(
        forceRefresh: Boolean,
        onFetchSuccess: () -> Unit,
        onFetchRemoteFailed: (Throwable) -> Unit
    ): Flow<DataState<List<Wallpaper>>>

    fun getDaily(
        categoryName: String = Constants.DEFAULT_DAILY_CATEGORY,
        forceRefresh: Boolean,
        onFetchSuccess: () -> Unit,
        onFetchRemoteFailed: (Throwable) -> Unit
    ): Flow<DataState<List<Wallpaper>>>

    fun getWallpapersOfCategory(
        categoryName: String,
        forceRefresh: Boolean,
        onFetchSuccess: () -> Unit,
        onFetchRemoteFailed: (Throwable) -> Unit
    ): Flow<DataState<List<Wallpaper>>>

    suspend fun deleteNonFavoriteWallpapersOlderThan(timestampInMillis: Long)

    suspend fun updateWallpaper(wallpaper: Wallpaper)
}