package com.adwi.interactors.wallpaper

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.adwi.datasource.local.WallpaperDatabase
import com.adwi.datasource.local.domain.SearchQueryRemoteKey
import com.adwi.datasource.local.domain.WallpaperEntity
import com.adwi.datasource.local.domain.toEntity
import com.adwi.datasource.local.domain.toSearchResult
import com.adwi.datasource.network.PexService
import com.adwi.datasource.network.domain.toDomain
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import java.io.IOException

private const val WALLPAPERS_STARTING_PAGE_INDEX = 1

@ExperimentalPagingApi
class SearchNewsRemoteMediator(
    private val searchQuery: String,
    private val pexService: PexService,
    private val database: WallpaperDatabase
) : RemoteMediator<Int, WallpaperEntity>() {

    private val wallpaperDao = database.wallpaperDao()
    private val searchDao = database.searchDao()


    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, WallpaperEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> WALLPAPERS_STARTING_PAGE_INDEX
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> this.searchDao.getRemoteKey(searchQuery).nextPage
        }

        try {
            val response = pexService.getSearch(searchQuery, page)
            delay(3000)
            val searchServerResults = response.wallpaperList

            val favoriteWallpapers = this.wallpaperDao.getAllFavorites().first()
            val searchResultWallpapers = searchServerResults.map { serverSearchResultWallpaper ->
                val isFavorite = favoriteWallpapers.any { favoriteWallpaper ->
                    favoriteWallpaper.id == serverSearchResultWallpaper.id
                }
                serverSearchResultWallpaper.toDomain(
                    categoryName = searchQuery,
                    isFavorite = isFavorite
                ).toEntity()
            }

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    this.searchDao.deleteSearchResultsForQuery(searchQuery)
                }

                val lastQueryPosition = this.searchDao.getLastQueryPosition(searchQuery) ?: 0
                var queryPosition = lastQueryPosition + 1

                val searchResults = searchResultWallpapers.map {
                    it.toSearchResult(
                        searchQuery = searchQuery,
                        queryPosition = queryPosition++
                    )
                }

                val nextPageKey = page + 1
                database.withTransaction {
                    wallpaperDao.insertWallpapers(searchResultWallpapers)
                    searchDao.insertSearchResults(searchResults)
                    searchDao.insertRemoteKey(SearchQueryRemoteKey(searchQuery, nextPageKey))
                }
            }

            return MediatorResult.Success(endOfPaginationReached = searchServerResults.isEmpty())
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }
}