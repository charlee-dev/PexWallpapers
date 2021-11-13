package com.adwi.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.adwi.components.domain.ColorsState
import com.adwi.components.domain.WallpaperListState
import com.adwi.components.domain.WallpaperState
import com.adwi.core.domain.ProgressBarState

data class HomeState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val daily: MutableState<WallpaperState> = mutableStateOf(WallpaperState()),
    val colors: MutableState<ColorsState> = mutableStateOf(ColorsState()),
    val curated: MutableState<WallpaperListState> = mutableStateOf(WallpaperListState())
)