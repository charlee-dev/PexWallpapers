package com.adwi.components.domain

import com.adwi.core.domain.ProgressBarState
import com.adwi.domain.ColorCategory


data class ColorsState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val categories: List<ColorCategory> = listOf()
)