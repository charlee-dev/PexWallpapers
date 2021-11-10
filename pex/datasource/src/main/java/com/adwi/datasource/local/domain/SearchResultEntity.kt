package com.adwi.datasource.local.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.adwi.domain.Wallpaper

@Entity(tableName = "search_results", primaryKeys = ["searchQuery", "wallpaperId"])
data class SearchResultEntity(
    val searchQuery: String,
    val wallpaperId: Int,
    val queryPosition: Int
)

@Entity(tableName = "search_query_remote_keys")
data class SearchQueryRemoteKey(
    @PrimaryKey val searchQuery: String,
    val nextPage: Int
)

fun wallpaperToSearchResult(searchQuery: String, wallpaper: Wallpaper, queryPosition: Int) =
    SearchResultEntity(
        searchQuery = searchQuery,
        wallpaperId = wallpaper.id,
        queryPosition = queryPosition
    )