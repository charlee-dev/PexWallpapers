package com.adwi.home

import com.adwi.core.domain.LoadingState
import com.adwi.domain.Wallpaper

data class DailyState(
    val loading: LoadingState = LoadingState.Idle,
    val daily: List<Wallpaper> = listOf(),
    val error: Throwable = Throwable()
)
