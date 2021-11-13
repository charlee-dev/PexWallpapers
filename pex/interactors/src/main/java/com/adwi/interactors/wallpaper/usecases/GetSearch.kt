package com.adwi.interactors.wallpaper.usecases

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.adwi.core.util.Constants.PAGING_MAX_SIZE
import com.adwi.core.util.Constants.PAGING_SIZE
import com.adwi.datasource.local.WallpaperDatabase
import com.adwi.datasource.local.domain.WallpaperEntity
import com.adwi.datasource.network.PexService
import com.adwi.interactors.wallpaper.SearchNewsRemoteMediator
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
class GetSearch @Inject constructor(
    private val service: PexService,
    private val database: WallpaperDatabase
) {
    private val searchDao = database.searchDao()

    fun getSearchResultsPaged(query: String): Flow<PagingData<WallpaperEntity>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGING_SIZE,
                maxSize = PAGING_MAX_SIZE
            ),
            remoteMediator = SearchNewsRemoteMediator(query, service, database),
            pagingSourceFactory = { searchDao.getSearchResultWallpaperPaged(query) }
        ).flow
}