package com.adwi.search

import androidx.paging.PagingData
import com.adwi.core.domain.ProgressBarState
import com.adwi.datasource.local.domain.WallpaperEntity

data class PagingState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val wallpapersPaged: PagingData<WallpaperEntity> = PagingData.empty()
)
