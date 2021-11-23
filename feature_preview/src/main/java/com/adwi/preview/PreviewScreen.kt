package com.adwi.preview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.paging.ExperimentalPagingApi
import coil.annotation.ExperimentalCoilApi
import com.adwi.components.*
import com.adwi.components.theme.paddingValues
import com.adwi.composables.R
import com.adwi.core.domain.Event
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber

@ExperimentalCoroutinesApi
@ExperimentalCoilApi
@ExperimentalMaterialApi
@ExperimentalPagingApi
@Composable
fun PreviewScreen(
    viewModel: PreviewViewModel,
    onTriggerEvent: (PreviewEvent) -> Unit,
    onSetWallpaperClick: (Int) -> Unit,
    upPress: () -> Unit
) {
    val wallpaper by viewModel.wallpaper.collectAsState(null)

    val context = LocalContext.current

    PexScaffold(
        viewModel = viewModel
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
            wallpaper?.let {
                PreviewCard(
                    imageUrl = it.imageUrlPortrait,
                    wallpaperId = it.id,
                    modifier = Modifier
                        .padding(horizontal = paddingValues)
                        .padding(vertical = paddingValues / 2)
                        .weight(1f),
                    onLongPress = {
                        onTriggerEvent(PreviewEvent.OnFavoriteClick(it))
                        Timber.d(it.isFavorite.toString())
                    },
                    isHeartEnabled = it.isFavorite
                )
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(paddingValues / 2),
                    text = stringResource(id = R.string.photo_by, it.photographer)
                )
                ImageActionButtons(
                    modifier = Modifier.fillMaxWidth(),
                    onUrlClick = { onTriggerEvent(PreviewEvent.GoToPexels(it)) },
                    onSaveClick = {
                        onTriggerEvent(PreviewEvent.DownloadWallpaper(it))
                        onTriggerEvent(
                            PreviewEvent.ShowMessageEvent(
                                Event.ShowSnackBar(
                                    context.getString(R.string.automation_saved)
                                )
                            )
                        )
                    },
                    onFavoriteClick = { onTriggerEvent(PreviewEvent.OnFavoriteClick(it)) },
                    isFavorite = it.isFavorite
                )
                PexButton(
                    onClick = {
//                    onSetWallpaperClick(wallpaper.id)
                        onTriggerEvent(
                            PreviewEvent.SetWallpaper(
                                imageUrl = it.imageUrlPortrait,
                                setHomeScreen = true,
                                setLockScreen = false
                            )
                        )
                    },
                    text = stringResource(id = R.string.set_wallpaper),
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxWidth()
                )
            }
        }
    }
}