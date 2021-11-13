package com.adwi.preview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.paging.ExperimentalPagingApi
import coil.annotation.ExperimentalCoilApi
import com.adwi.components.*
import com.adwi.components.theme.paddingValues
import com.adwi.composables.R
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalCoilApi
@ExperimentalMaterialApi
@ExperimentalPagingApi
@Composable
fun PreviewScreen(
    state: PreviewState,
    onSetWallpaperClick: (Int) -> Unit,
    upPress: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Header(modifier = Modifier)
        state.wallpaper?.let { wallpaper ->
            PreviewCard(
                imageUrl = wallpaper.imageUrlPortrait,
                modifier = Modifier
                    .padding(horizontal = paddingValues)
                    .padding(vertical = paddingValues / 2)
                    .weight(1f)
            )
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(paddingValues / 2), text = "Photo by ${wallpaper.photographer}"
            )
            ImageActionButtons(
                modifier = Modifier
                    .fillMaxWidth(),
                onUrlClick = {},
                onSaveClick = {},
                onFavoriteClick = {}
            )
            PexButton(
                onClick = { onSetWallpaperClick(wallpaper.id) },
                text = stringResource(id = R.string.set_wallpaper),
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxWidth()
            )
        } ?: PexLoading(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
    }
}