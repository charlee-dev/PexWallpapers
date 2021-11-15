package com.adwi.home

import com.adwi.domain.Wallpaper

sealed class HomeEvent {
    object GetDaily : HomeEvent()
    object GetColors : HomeEvent()
    object GetCurated : HomeEvent()
    object Refresh : HomeEvent()
    data class SetCategory(val categoryName: String) : HomeEvent()
    data class OnFavoriteClick(val wallpaper: Wallpaper) : HomeEvent()
}
