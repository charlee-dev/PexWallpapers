package com.adwi.search

import com.adwi.domain.Wallpaper

sealed class SearchEvents {
    object GetSearch : SearchEvents()
    data class OnFavoriteClick(val wallpaper: Wallpaper) : SearchEvents()

    object RestoreLastQuery : SearchEvents()
    data class UpdateSavedQuery(val query: String) : SearchEvents()
    data class OnSearchQuerySubmit(val query: String) : SearchEvents()
}
