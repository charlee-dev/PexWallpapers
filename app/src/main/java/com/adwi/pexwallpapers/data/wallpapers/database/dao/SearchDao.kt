package com.adwi.pexwallpapers.data.wallpapers.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.adwi.pexwallpapers.data.wallpapers.database.domain.SearchQueryRemoteKey
import com.adwi.pexwallpapers.data.wallpapers.database.domain.SearchResultEntity
import com.adwi.pexwallpapers.data.wallpapers.database.domain.WallpaperEntity

@Dao
interface SearchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchResults(searchResults: List<SearchResultEntity>)

    @Query("SELECT * FROM search_results INNER JOIN wallpaper_table ON id = wallpaperId WHERE searchQuery = :query ORDER BY queryPosition")
    fun getSearchResultWallpaperPaged(query: String): PagingSource<Int, WallpaperEntity>

    @Query("SELECT MAX(queryPosition) FROM search_results WHERE searchQuery = :searchQuery")
    suspend fun getLastQueryPosition(searchQuery: String): Int?

    @Query("DELETE FROM search_results WHERE searchQuery = :query")
    suspend fun deleteSearchResultsForQuery(query: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRemoteKey(remoteKey: SearchQueryRemoteKey)

    @Query("SELECT * FROM search_query_remote_keys WHERE searchQuery = :searchQuery")
    suspend fun getRemoteKey(searchQuery: String): SearchQueryRemoteKey
}