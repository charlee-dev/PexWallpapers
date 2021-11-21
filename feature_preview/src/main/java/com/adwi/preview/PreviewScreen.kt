package com.adwi.preview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
    viewModel: PreviewViewModel,
    onSetWallpaperClick: (Int) -> Unit,
    upPress: () -> Unit
) {
    val wallpaper by viewModel.wallpaper.collectAsState()

    val scaffoldState = rememberScaffoldState()

    PexScaffold(
        viewModel = viewModel,
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Header(
                modifier = Modifier.padding(
                    horizontal = paddingValues,
                    vertical = paddingValues / 2
                ),
                title = stringResource(id = R.string.preview),
                icon = Icons.Outlined.Image,
                actionIcon = null
            )
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
                    .padding(paddingValues / 2),
                text = "Photo by ${wallpaper.photographer}"
            )
            ImageActionButtons(
                modifier = Modifier
                    .fillMaxWidth(),
                onUrlClick = {},
                onSaveClick = {},
                onFavoriteClick = {}
            )
            PexButton(
                onClick = {
//                    onSetWallpaperClick(wallpaper.id)
                    viewModel.setSnackBar("Not implemented yet")
                },
                text = stringResource(id = R.string.set_wallpaper),
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxWidth()
            )
        }
    }
}