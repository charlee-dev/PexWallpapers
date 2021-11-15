package com.adwi.components.domain

import com.adwi.core.domain.LoadingState
import com.adwi.domain.Wallpaper


data class WallpaperListState(
    val loadingState: LoadingState = LoadingState.Idle,
    val wallpapers: List<Wallpaper> = listOf()
)
