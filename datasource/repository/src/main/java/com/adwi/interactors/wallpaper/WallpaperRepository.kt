package com.adwi.interactors.wallpaper

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.adwi.core.domain.Resource
import com.adwi.core.util.Constants.DEFAULT_DAILY_CATEGORY
import com.adwi.datasource.local.domain.WallpaperEntity
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
    ): Flow<Resource<List<Wallpaper>>>

    fun getDaily(
        categoryName: String = DEFAULT_DAILY_CATEGORY,
        forceRefresh: Boolean,
        onFetchSuccess: () -> Unit,
        onFetchRemoteFailed: (Throwable) -> Unit
    ): Flow<Resource<List<Wallpaper>>>

    fun getColors(
        forceRefresh: Boolean,
        onFetchSuccess: () -> Unit,
        onFetchRemoteFailed: (Throwable) -> Unit
    ): Flow<Resource<List<ColorCategory>>>

    fun getWallpapersOfCategory(
        categoryName: String,
        forceRefresh: Boolean,
        onFetchSuccess: () -> Unit,
        onFetchRemoteFailed: (Throwable) -> Unit
    ): Flow<Resource<List<Wallpaper>>>

    fun getSearch(query: String): Flow<PagingData<WallpaperEntity>> // TODO("change it for toDomain")

     fun getFavorites(): Flow<List<Wallpaper>>

    fun getWallpaperById(id: Int): Flow<Wallpaper>

    suspend fun updateWallpaper(wallpaper: Wallpaper)
}