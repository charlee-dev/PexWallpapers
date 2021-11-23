package com.adwi.home

import com.adwi.core.domain.Event
import com.adwi.domain.Wallpaper

sealed class HomeEvent {

    data class SetCategory(val categoryName: String) : HomeEvent()
    data class OnFavoriteClick(val wallpaper: Wallpaper) : HomeEvent()

    object OnStart : HomeEvent()
    object ManualRefresh : HomeEvent()
    data class ShowMessageEvent(val event: Event) : HomeEvent()
}
