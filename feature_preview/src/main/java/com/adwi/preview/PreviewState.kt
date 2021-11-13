package com.adwi.preview

import com.adwi.core.domain.ProgressBarState
import com.adwi.domain.Wallpaper

data class PreviewState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val wallpaper: Wallpaper? = null,
)