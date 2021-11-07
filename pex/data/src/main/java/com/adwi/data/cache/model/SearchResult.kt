package com.adwi.data.cache.model

import com.adwi.data.cache.SearchResult
import com.adwi.data.network.model.WallpaperDto

fun wallpaperToSearchResult(searchQuery: String, wallpaper: WallpaperDto, queryPosition: Int) =
    SearchResult(
        searchQuery = searchQuery,
        wallpaperId = wallpaper.id.toLong(),
        queryPosition = queryPosition
    )