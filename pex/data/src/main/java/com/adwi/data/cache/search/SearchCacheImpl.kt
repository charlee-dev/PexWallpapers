package com.adwi.data.cache.search

import com.adwi.data.cache.SearchQueryRemoteKeys
import com.adwi.data.cache.SearchResult
import com.adwi.data.cache.WallpaperDb
import com.adwi.data.cache.WallpaperDbQueries

class SearchCacheImpl(
    private val wallpaperDb: WallpaperDb
) : SearchCache {

    private var queries: WallpaperDbQueries = wallpaperDb.wallpaperDbQueries

    override suspend fun insertSearchResult(searchResult: SearchResult) {
        return searchResult.run {
            queries.insertSearchResult(searchQuery, wallpaperId, queryPosition)
        }
    }

//    override fun getSearchResultWallpaperPaged(query: String): PagingSource<Int, Wallpaper> {
//        return queries.getSearchResultWallpaperPaged(query).executeAsList()
//    }

//    override suspend fun getLastQueryPosition(searchQuery: String): Int? {
//        return queries.getLastQueryPosition(searchQuery)
//    }

    override suspend fun deleteSearchResultsForQuery(query: String) {
        return queries.deleteSearchResultsForQuery(query)
    }

    override suspend fun insertRemoteKey(remoteKey: SearchQueryRemoteKeys) {
        return remoteKey.run {
            queries.insertRemoteKey(this.searchQuery, this.nextPage)
        }
    }

    override suspend fun getRemoteKey(searchQuery: String): SearchQueryRemoteKeys {
        return queries.getRemoteKey(searchQuery).executeAsOne()
    }
}