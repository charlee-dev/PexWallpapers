package com.adwi.preview

import androidx.compose.foundation.layout.Box
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.annotation.ExperimentalCoilApi
import com.adwi.components.PexCoilImage
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun PreviewScreen(
    viewModel: PreviewViewModel = viewModel(),
//    onWallpaperClick: (Int) -> Unit,
//    onCategoryClick: () -> Unit,
    upPress: () -> Unit
) {
    val preview by viewModel.preview.collectAsState()

    Box(modifier = Modifier) {
        PexCoilImage(imageUrl = preview.imageUrlPortrait)
    }
}
