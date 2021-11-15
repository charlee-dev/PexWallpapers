package com.adwi.components.domain

import com.adwi.core.domain.LoadingState
import com.adwi.domain.Wallpaper

data class WallpaperState(
    val loadingState: LoadingState = LoadingState.Idle,
    val wallpaper: Wallpaper = Wallpaper.defaultDaily
)
