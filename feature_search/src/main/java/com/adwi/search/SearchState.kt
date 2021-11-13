package com.adwi.search

import androidx.paging.PagingData
import com.adwi.core.domain.ProgressBarState
import com.adwi.datasource.local.domain.WallpaperEntity

data class SearchState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val wallpapers: PagingData<WallpaperEntity>? = null
)
