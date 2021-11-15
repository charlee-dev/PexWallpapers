package com.adwi.favorites

import com.adwi.domain.Wallpaper

sealed class FavoritesEvent {
    object GetFavorites : FavoritesEvent()
    data class OnFavoriteClick(val wallpaper: Wallpaper) : FavoritesEvent()
}
