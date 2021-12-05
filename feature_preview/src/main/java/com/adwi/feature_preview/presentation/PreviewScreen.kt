package com.adwi.feature_preview.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.*
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.ExperimentalPagingApi
import coil.annotation.ExperimentalCoilApi
import com.adwi.components.*
import com.adwi.components.theme.Dimensions
import com.adwi.components.theme.paddingValues
import com.adwi.core.Resource
import com.adwi.feature_preview.R
import com.adwi.pexwallpapers.domain.model.Wallpaper
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalAnimationApi
@ExperimentalCoroutinesApi
@ExperimentalCoilApi
@ExperimentalMaterialApi
@ExperimentalPagingApi
@Composable
fun PreviewScreen(
    viewModel: PreviewViewModel,
    upPress: () -> Unit,
    onShareClick: (Wallpaper) -> Unit,
    onDownloadClick: (Wallpaper) -> Unit,
    onSetWallpaperClick: (url: String, home: Boolean, lock: Boolean) -> Unit
) {
    val wallpaper by viewModel.wallpaper.collectAsState()
    val saveState by viewModel.saveState.collectAsState()

    val uriHandler = LocalUriHandler.current

    PexScaffold(
        viewModel = viewModel
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Header(
                hasUpPress = true,
                onUpPress = upPress,
                modifier = Modifier,
                title = stringResource(id = R.string.preview),
                icon = Icons.Outlined.Image,
                actionIcon = null
            )
            wallpaper?.let {
                val infiniteTransition = rememberInfiniteTransition()

                val scaleLoading by infiniteTransition.animateFloat(
                    initialValue = 1f,
                    targetValue = 1.02f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(500, easing = LinearOutSlowInEasing),
                        repeatMode = RepeatMode.Reverse
                    )
                )

                val scale = if (saveState == Resource.Loading()) scaleLoading else 1f

                Box(modifier = Modifier.weight(1f)) {
                    PreviewCard(
                        wallpaper = it,
                        modifier = Modifier
                            .padding(horizontal = paddingValues)
                            .padding(vertical = paddingValues / 2)
                            .graphicsLayer(
                                scaleX = scale,
                                scaleY = scale
                            )
                    )
                    androidx.compose.animation.AnimatedVisibility(
                        saveState is Resource.Success
                    ) {
                        PexLottieAnimatedView(
                            res = R.raw.completed,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }

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
                    onGoToUrlClick = { uriHandler.openUri(it.url) },
                    onShareClick = { onShareClick(it) },
                    onDownloadClick = { onDownloadClick(it) },
                    onFavoriteClick = { viewModel.onFavoriteClick(it) },
                    isFavorite = it.isFavorite
                )

                PexButton(
                    state = saveState,
                    onClick = { onSetWallpaperClick(it.imageUrlPortrait, true, false) },
                    text = stringResource(id = R.string.set_wallpaper),
                    loadingText = stringResource(R.string.loading),
                    errorText = stringResource(R.string.error),
                    successText = stringResource(R.string.wallpaper_has_been_set),
                    shape = RoundedCornerShape(
                        topStartPercent = 100,
                        topEndPercent = 100
                    ),
                    modifier = Modifier
                        .padding(top = paddingValues)
                        .fillMaxWidth()
                        .height(56.dp)
                )
            }
        }
    }
}