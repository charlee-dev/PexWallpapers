package com.adwi.feature_preview.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.ZoomOutMap
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.adwi.components.theme.MenuItems
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
    onSetWallpaperClick: (url: String, home: Boolean, lock: Boolean) -> Unit,
    onGiveFeedbackClick: () -> Unit,
    onRequestFeature: () -> Unit,
    onReportBugClick: () -> Unit
) {
    val wallpaper by viewModel.wallpaper.collectAsState()
    val saveState by viewModel.saveState.collectAsState()

    val uriHandler = LocalUriHandler.current

    val infiniteTransition = rememberInfiniteTransition()

    var inPreview by rememberSaveable { mutableStateOf(false) }
    val transition = updateTransition(targetState = inPreview, label = "Preview")

    val translationY by transition.animateFloat(label = "TranslationY") { state ->
        if (state) 1.3f else 1f
    }
    val alpha by transition.animateFloat(label = "Alpha") { state ->
        if (state) 0f else 1f
    }
    val paddingState by transition.animateDp(label = "Alpha") { state ->
        if (state) paddingValues / 2 else paddingValues
    }

    val scaleLoading by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.02f,
        animationSpec = infiniteRepeatable(
            animation = tween(500, easing = LinearOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val scale = if (saveState == Resource.Loading()) scaleLoading else 1f

    PexScaffold(
        viewModel = viewModel
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedVisibility(!inPreview) {
                PexExpandableAppBar(
                    hasUpPress = true,
                    onUpPress = upPress,
                    modifier = Modifier,
                    title = stringResource(id = R.string.preview),
                    icon = Icons.Outlined.Image,
                    showShadows = viewModel.showShadows
                ) {
                    MenuListItem(
                        action = onGiveFeedbackClick,
                        item = MenuItems.GiveFeedback
                    )
                    MenuListItem(
                        action = onRequestFeature,
                        item = MenuItems.RequestFeature
                    )
                    MenuListItem(
                        action = onReportBugClick,
                        item = MenuItems.ReportBug
                    )
                    MenuListItem(
                        action = { viewModel.setSnackBar("Not implemented yet") },
                        item = MenuItems.ShowTips
                    )
                }
            }
            wallpaper?.let {

                PreviewCard(
                    wallpaper = it,
                    showShadows = viewModel.showShadows,
                    onWallpaperClick = { inPreview = !inPreview },
                    onLongPress = { viewModel.onFavoriteClick(it) },
                    modifier = Modifier
                        .padding(horizontal = paddingState)
                        .padding(vertical = paddingState)
                        .weight(1f)
                        .graphicsLayer(
                            scaleX = scale,
                            scaleY = scale
                        ),
                    inPreview = inPreview
                )

                AnimatedVisibility(!inPreview) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(vertical = paddingValues / 2)
                                .graphicsLayer(
                                    translationY = translationY,
                                    alpha = alpha
                                ),
                            horizontalArrangement = Arrangement.spacedBy(Dimensions.medium),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(id = R.string.photo_by),
                                color = MaterialTheme.colors.onBackground
                            )
                            Text(
                                text = it.photographer,
                                color = MaterialTheme.colors.onBackground
                            )
                        }
                        ImageActionButtons(
                            modifier = Modifier
                                .fillMaxWidth()
                                .graphicsLayer(
                                    translationY = translationY,
                                    alpha = alpha
                                ),
                            onGoToUrlClick = { uriHandler.openUri(it.url) },
                            onShareClick = { onShareClick(it) },
                            onDownloadClick = { onDownloadClick(it) },
                            onFavoriteClick = { viewModel.onFavoriteClick(it) },
                            isFavorite = it.isFavorite
                        )
                    }
                }
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
                    showShadows = viewModel.showShadows,
                    modifier = Modifier
                        .padding(top = paddingState)
                        .fillMaxWidth()
                        .height(56.dp)
                )
            }
        }
    }
}