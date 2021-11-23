package com.adwi.home

import com.adwi.core.domain.LoadingState

data class HomeState(
    val loading: LoadingState = LoadingState.Idle,
    val dailyState: DailyState = DailyState(),
    val colorsState: ColorsState = ColorsState(),
    val curatedState: ColorsState = ColorsState(),
)