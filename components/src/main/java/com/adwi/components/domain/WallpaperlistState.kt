package com.adwi.components.domain

import com.adwi.core.domain.ProgressBarState
import com.adwi.domain.Wallpaper


data class WallpaperListState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val wallpapers: List<Wallpaper> = listOf()
)
