package com.adwi.favorites

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.adwi.components.domain.WallpaperListState
import com.adwi.core.domain.LoadingState

data class FavoritesState(
    val loadingState: LoadingState = LoadingState.Idle,
    val favorites: MutableState<WallpaperListState> = mutableStateOf(WallpaperListState())
)
