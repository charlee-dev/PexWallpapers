package com.adwi.search

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.adwi.core.domain.ProgressBarState

data class SearchState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val wallpapers: MutableState<PagingState> = mutableStateOf(PagingState())
)
