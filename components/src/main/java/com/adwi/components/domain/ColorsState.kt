package com.adwi.components.domain

import com.adwi.core.domain.LoadingState
import com.adwi.domain.ColorCategory


data class ColorsState(
    val loadingState: LoadingState = LoadingState.Idle,
    val categories: List<ColorCategory> = listOf()
)