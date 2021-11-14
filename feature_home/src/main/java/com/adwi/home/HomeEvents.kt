package com.adwi.home

import com.adwi.domain.Wallpaper

sealed class HomeEvents {
    object GetDaily : HomeEvents()
    object GetColors : HomeEvents()
    object GetCurated : HomeEvents()
    object Refresh : HomeEvents()
    data class SetCategory(val categoryName: String) : HomeEvents()
    data class OnFavoriteClick(val wallpaper: Wallpaper) : HomeEvents()
}
