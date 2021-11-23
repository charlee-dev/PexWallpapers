package com.adwi.home

import com.adwi.core.domain.Event
import com.adwi.domain.Wallpaper

sealed class HomeEvent {

    object DeleteOldNonFavoriteWallpapers : HomeEvent()
    object ManualRefresh : HomeEvent()
    object OnStart : HomeEvent()

    data class SetCategory(val categoryName: String) : HomeEvent()
    data class OnFavoriteClick(val wallpaper: Wallpaper) : HomeEvent()

    data class ShowMessageEvent(val event: Event) : HomeEvent()
}
