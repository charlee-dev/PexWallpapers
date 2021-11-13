package com.adwi.components.domain

import androidx.paging.PagingData
import com.adwi.core.domain.ProgressBarState
import com.adwi.domain.Wallpaper

data class PagingState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val wallpapersPaged: PagingData<Wallpaper>
)