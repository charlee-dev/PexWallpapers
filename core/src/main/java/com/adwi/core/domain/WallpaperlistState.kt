package com.adwi.core.domain

import com.adwi.domain.Wallpaper


data class WallpaperListState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val wallpapers: List<Wallpaper> = listOf()
)
