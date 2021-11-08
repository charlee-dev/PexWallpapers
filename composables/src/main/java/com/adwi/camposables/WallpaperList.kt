package com.adwi.camposables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.adwi.core.domain.ProgressBarState
import com.adwi.core.domain.WallpaperListState

@Composable
fun WallpaperList(
    state: WallpaperListState
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(state.wallpapers) { wallpaper ->
                Text(text = wallpaper.photographer)
            }
        }
        if (state.progressBarState is ProgressBarState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}