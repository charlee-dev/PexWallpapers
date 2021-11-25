package com.adwi.repository.wallpaper

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.adwi.pexwallpapers.data.wallpapers.database.WallpaperDatabase
import com.adwi.pexwallpapers.data.wallpapers.database.domain.SearchQueryRemoteKey
import com.adwi.pexwallpapers.data.wallpapers.database.domain.WallpaperEntity
import com.adwi.pexwallpapers.data.wallpapers.database.domain.toEntity
import com.adwi.pexwallpapers.data.wallpapers.database.domain.toSearchResult
import com.adwi.pexwallpapers.data.wallpapers.network.PexService
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException

private const val WALLPAPERS_STARTING_PAGE_INDEX = 1

@ExperimentalPagingApi
class SearchNewsRemoteMediator(
    private val searchQuery: String,
    private val pexService: com.adwi.pexwallpapers.data.wallpapers.network.PexService,
    private val database: com.adwi.pexwallpapers.data.wallpapers.database.WallpaperDatabase
) : RemoteMediator<Int, com.adwi.pexwallpapers.data.wallpapers.database.domain.WallpaperEntity>() {

    private val wallpaperDao = database.wallpaperDao()
    private val searchDao = database.searchDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, com.adwi.pexwallpapers.data.wallpapers.database.domain.WallpaperEntity>
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

            val searchResultWallpapers = searchServerResults.keepFavorites(
                categoryName = searchQuery,
                favoritesList = favoriteWallpapers
            )
            val searchResultWallpaperEntities = searchResultWallpapers.map { it.toEntity() }

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
                    wallpaperDao.insertWallpapers(searchResultWallpaperEntities)
                    searchDao.insertSearchResults(searchResults)
                    searchDao.insertRemoteKey(
                        com.adwi.pexwallpapers.data.wallpapers.database.domain.SearchQueryRemoteKey(
                            searchQuery,
                            nextPageKey
                        )
                    )
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