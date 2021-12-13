package com.adwi.feature_preview.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Image
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.paging.ExperimentalPagingApi
import coil.annotation.ExperimentalCoilApi
import com.adwi.components.*
import com.adwi.components.theme.Dimensions
import com.adwi.components.theme.MenuItems
import com.adwi.components.theme.paddingValues
import com.adwi.feature_preview.R
import com.adwi.feature_preview.presentation.components.*
import com.adwi.pexwallpapers.domain.model.Wallpaper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import timber.log.Timber

enum class SetWallpaperAnimationState { Idle, Preview, Flash, Wipe }

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
    onHomeClick: (String) -> Unit,
    onLockClick: (String) -> Unit,
    onGiveFeedbackClick: () -> Unit,
    onRequestFeature: () -> Unit,
    onReportBugClick: () -> Unit
) {
    val wallpaper by viewModel.wallpaper.collectAsState()

    val uriHandler = LocalUriHandler.current

    // Preview
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

    // Set wallpaper
    var setWallpaperAnimationState by remember { mutableStateOf(SetWallpaperAnimationState.Idle) }

    var flash by remember { mutableStateOf(false) }
    var wipe by remember { mutableStateOf(true) }
    var showCurrent by remember { mutableStateOf(false) }
    var bitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(setWallpaperAnimationState) {
        when (setWallpaperAnimationState) {
            SetWallpaperAnimationState.Idle -> {
                Timber.d("State = Idle ${bitmap?.height}")
            }
            SetWallpaperAnimationState.Preview -> {
                Timber.d("State = Preview")
                inPreview = true
                delay(TRANSITION_DURATION.toLong())
                setWallpaperAnimationState = SetWallpaperAnimationState.Flash
            }
            SetWallpaperAnimationState.Flash -> {
                Timber.d("State = Flash")
                flash = true
                showCurrent = true

                delay(FLASH_DURATION.toLong())
                flash = false
                setWallpaperAnimationState = SetWallpaperAnimationState.Wipe
            }
            SetWallpaperAnimationState.Wipe -> {
                Timber.d("State = Wipe")
                inPreview = false
                delay(1000)
                wipe = false
                delay(WIPE_DURATION.toLong())
                showCurrent = false
                bitmap = null
                wipe = true
                onHomeClick(wallpaper!!.imageUrlPortrait)
                delay(WIPE_DURATION.toLong())
                setWallpaperAnimationState = SetWallpaperAnimationState.Idle
            }
        }
    }

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
                        .padding(top = paddingValues / 2, bottom = paddingState / 2)
                        .weight(1f),
                    inPreview = inPreview,
                    isFlash = flash,
                    imageBitmap = bitmap,
                    isWipe = wipe,
                    showCurrent = showCurrent,
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
                SetWallpaperButton(
                    onHomeClick = {
                        bitmap = viewModel.getCurrentWallpaper(true)
                        setWallpaperAnimationState = SetWallpaperAnimationState.Preview
//                        onHomeClick(it.imageUrlPortrait)
                    },
                    onLockClick = {
                        bitmap = viewModel.getCurrentWallpaper(false)
                        setWallpaperAnimationState = SetWallpaperAnimationState.Preview
                        onLockClick(it.imageUrlPortrait)
                                  },
                    showShadows = viewModel.showShadows,
                    enabled = setWallpaperAnimationState == SetWallpaperAnimationState.Idle,
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxWidth()
                        .height(Dimensions.Button)
                )
            }
        }
    }
}