package com.adwi.home

import androidx.compose.runtime.Composable
import com.adwi.camposables.WallpaperList


@Composable
fun HomeScreen(
    viewModel: HomeViewModel
) {
    val state = viewModel.state
    WallpaperList(
        state = state.value
    )
}