package com.adwi.components.domain

import androidx.paging.PagingData
import com.adwi.core.domain.LoadingState
import com.adwi.domain.Wallpaper

data class PagingState(
    val loadingState: LoadingState = LoadingState.Idle,
    val wallpapersPaged: PagingData<Wallpaper>
)