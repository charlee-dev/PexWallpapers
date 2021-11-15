package com.adwi.preview

import com.adwi.core.domain.LoadingState
import com.adwi.domain.Wallpaper

data class PreviewState(
    val loadingState: LoadingState = LoadingState.Idle,
    val wallpaper: Wallpaper? = null,
)