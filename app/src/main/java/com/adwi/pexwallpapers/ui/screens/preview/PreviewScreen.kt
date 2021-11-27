package com.adwi.pexwallpapers.ui.screens.preview

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateInt
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.ExperimentalPagingApi
import coil.annotation.ExperimentalCoilApi
import com.adwi.pexwallpapers.R
import com.adwi.pexwallpapers.model.Wallpaper
import com.adwi.pexwallpapers.model.state.Result
import com.adwi.pexwallpapers.ui.components.*
import com.adwi.pexwallpapers.ui.theme.Dimensions
import com.adwi.pexwallpapers.ui.theme.paddingValues
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalAnimationApi
@ExperimentalCoroutinesApi
@ExperimentalCoilApi
@ExperimentalMaterialApi
@ExperimentalPagingApi
@Composable
fun PreviewScreen(
    viewModel: PreviewViewModel,
    onGoToUrlClick: (String) -> Unit,
    onShareClick: (Wallpaper) -> Unit,
    onDownloadClick: (Wallpaper) -> Unit,
    upPress: () -> Unit,
) {
    val wallpaper by viewModel.wallpaper.collectAsState()
    val saveState by viewModel.saveState.collectAsState()

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
                    wallpaper = it,
                    modifier = Modifier
                        .padding(horizontal = paddingValues)
                        .padding(vertical = paddingValues / 2)
                        .weight(1f)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .padding(vertical = paddingValues / 2),
                        text = stringResource(id = R.string.photo_by),
                        color = MaterialTheme.colors.onBackground
                    )
                    Text(
                        modifier = Modifier.padding(start = Dimensions.small),
                        text = it.photographer,
                        color = MaterialTheme.colors.onBackground
                    )
                }
                ImageActionButtons(
                    modifier = Modifier.fillMaxWidth(),
                    onGoToUrlClick = { onGoToUrlClick(it.url) },
                    onShareClick = { onShareClick(it) },
                    onDownloadClick = { onDownloadClick(it) },
                    onFavoriteClick = { viewModel.onFavoriteClick(it) },
                    isFavorite = it.isFavorite
                )

                val transition = updateTransition(targetState = saveState, label = "Button state")

                val roundedEdgeSize by transition.animateInt(
                    label = "Card background color"
                ) { result ->
                    when (result) {
                        Result.Loading -> 80
                        Result.Success -> 90
                        else -> 100
                    }
                }

                val roundedTopShape =
                    RoundedCornerShape(
                        topStartPercent = roundedEdgeSize,
                        topEndPercent = roundedEdgeSize
                    )

                PexButton(
                    state = saveState,
                    onClick = {
                        viewModel.setWallpaper(
                            imageUrl = it.imageUrlPortrait,
                            setHomeScreen = true,
                            setLockScreen = false
                        )
                    },
                    text = stringResource(id = R.string.set_wallpaper),
                    successText = stringResource(id = R.string.wallpaper_has_been_set),
                    shape = roundedTopShape,
                    modifier = Modifier
                        .padding(top = paddingValues)
                        .fillMaxWidth()
                        .height(56.dp)
                )
            }
        }
    }
}