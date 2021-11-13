package com.adwi.preview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.annotation.ExperimentalCoilApi
import com.adwi.components.Header
import com.adwi.components.ImageActionButtons
import com.adwi.components.PexButton
import com.adwi.components.PreviewCard
import com.adwi.components.theme.paddingValues
import com.adwi.composables.R
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun PreviewScreen(
    viewModel: PreviewViewModel = viewModel(),
    onSetWallpaperClick: (Int) -> Unit,
    upPress: () -> Unit
) {
    val preview by viewModel.preview.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Header(modifier = Modifier)
        PreviewCard(
            imageUrl = preview.imageUrlPortrait,
            modifier = Modifier
                .padding(horizontal = paddingValues)
                .padding(vertical = paddingValues / 2)
                .weight(1f)
        )
        WallpaperByPhotographer(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(paddingValues / 2),
            text = preview.photographer
        )
        ImageActionButtons(
            modifier = Modifier
                .fillMaxWidth(),
            onUrlClick = {},
            onSaveClick = {},
            onFavoriteClick = {}
        )
        PexButton(
            onClick = { onSetWallpaperClick(preview.id) },
            text = stringResource(id = R.string.set_wallpaper),
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
        )
    }
}

@Composable
fun WallpaperByPhotographer(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(modifier = modifier, text = "Photo by $text")
}

@ExperimentalCoroutinesApi
@ExperimentalCoilApi
@ExperimentalMaterialApi
@Preview
@Composable
private fun PreviewScreenPreview() {
    MaterialTheme {
        PreviewScreen(onSetWallpaperClick = {}, upPress = {})
    }
}