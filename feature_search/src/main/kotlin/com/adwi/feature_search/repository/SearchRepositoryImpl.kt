package com.adwi.feature_search.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.adwi.data.database.WallpaperDatabase
import com.adwi.data.database.domain.WallpaperEntity
import com.adwi.data.database.domain.toEntity
import com.adwi.data.network.PexService
import com.adwi.data.util.Constants
import com.adwi.pexwallpapers.domain.model.Wallpaper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
class SearchRepositoryImpl @Inject constructor(
    private val service: PexService,
    private val database: WallpaperDatabase
) : SearchRepository {

    private val searchDao = database.searchDao()
    private val wallpapersDao = database.wallpaperDao()


    override fun getSearch(query: String): Flow<PagingData<WallpaperEntity>> =
        Pager(
            config = PagingConfig(
                pageSize = Constants.PAGING_SIZE,
                maxSize = Constants.PAGING_MAX_SIZE
            ),
            remoteMediator = SearchNewsRemoteMediator(query, service, database),
            pagingSourceFactory = { searchDao.getSearchResultWallpaperPaged(query) }
        ).flow

    override suspend fun updateWallpaper(wallpaper: Wallpaper) {
        wallpapersDao.updateWallpaper(wallpaper.toEntity())
    }
}