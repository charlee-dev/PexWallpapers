package com.adwi.repository.wallpaper

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.adwi.pexwallpapers.domain.DataState
import com.adwi.pexwallpapers.util.Constants.DEFAULT_DAILY_CATEGORY
import com.adwi.pexwallpapers.data.wallpapers.database.domain.WallpaperEntity
import com.adwi.domain.ColorCategory
import com.adwi.domain.Wallpaper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
interface WallpaperRepository {

    fun getCurated(
        forceRefresh: Boolean,
        onFetchSuccess: () -> Unit,
        onFetchRemoteFailed: (Throwable) -> Unit
    ): Flow<DataState<List<Wallpaper>>>

    fun getDaily(
        categoryName: String = DEFAULT_DAILY_CATEGORY,
        forceRefresh: Boolean,
        onFetchSuccess: () -> Unit,
        onFetchRemoteFailed: (Throwable) -> Unit
    ): Flow<DataState<List<Wallpaper>>>

    fun getColors(
        forceRefresh: Boolean,
        onFetchSuccess: () -> Unit,
        onFetchRemoteFailed: (Throwable) -> Unit
    ): Flow<DataState<List<ColorCategory>>>

    fun getWallpapersOfCategory(
        categoryName: String,
        forceRefresh: Boolean,
        onFetchSuccess: () -> Unit,
        onFetchRemoteFailed: (Throwable) -> Unit
    ): Flow<DataState<List<Wallpaper>>>

    fun getSearch(query: String): Flow<PagingData<com.adwi.pexwallpapers.data.wallpapers.database.domain.WallpaperEntity>> // TODO("change it for toDomain")

    fun getFavorites(): Flow<List<Wallpaper>>

    suspend fun resetAllFavorites()

    suspend fun deleteNonFavoriteWallpapersOlderThan(timestampInMillis: Long)

    fun getWallpaperById(id: Int): Flow<Wallpaper>

    suspend fun updateWallpaper(wallpaper: Wallpaper)
}