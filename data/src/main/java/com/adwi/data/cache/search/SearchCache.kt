package com.adwi.data.cache.search

import com.adwi.data.cache.SearchQueryRemoteKeys
import com.adwi.data.cache.SearchResult

interface SearchCache {

    // Search
    suspend fun insertSearchResult(searchResult: SearchResult)

//    fun getSearchResultWallpaperPaged(query: String): PagingSource<Int, Wallpaper>

//    suspend fun getLastQueryPosition(searchQuery: String): Int?

    suspend fun deleteSearchResultsForQuery(query: String)

    // SearchQueryRemoteKey
    suspend fun insertRemoteKey(remoteKey: SearchQueryRemoteKeys)

    suspend fun getRemoteKey(searchQuery: String): SearchQueryRemoteKeys
}