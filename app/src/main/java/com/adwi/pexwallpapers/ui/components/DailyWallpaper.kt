package com.adwi.pexwallpapers.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import coil.annotation.ExperimentalCoilApi
import com.adwi.pexwallpapers.R
import com.adwi.pexwallpapers.model.Wallpaper
import com.adwi.pexwallpapers.model.state.DataState
import com.adwi.pexwallpapers.ui.theme.PrimaryDark
import com.adwi.pexwallpapers.ui.theme.paddingValues
import com.google.accompanist.pager.*
import com.valentinilk.shimmer.shimmer
import kotlin.math.absoluteValue

@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalCoilApi
@Composable
fun DailyWallpaper(
    modifier: Modifier = Modifier,
    pagerState: PagerState = rememberPagerState(),
    dailyList: DataState<List<Wallpaper>>,
    elevation: Dp = 10.dp,
    shape: Shape = MaterialTheme.shapes.large,
    onWallpaperClick: (Int) -> Unit,
    onLongPress: (Wallpaper) -> Unit
) {
    BoxWithConstraints(
        modifier = modifier
    ) {
        val width = this.maxWidth

        DailyShimmer(
            width = width,
            visible = dailyList.data.isNullOrEmpty(),
            showErrorMessage = dailyList.data.isNullOrEmpty() && dailyList.error != null,
            message = stringResource(
                id = R.string.could_not_refresh,
                dailyList.error?.localizedMessage
                    ?: stringResource(R.string.unknown_error_occurred)
            )
        )

        dailyList.data?.let { list ->
            HorizontalPager(
                state = pagerState,
                count = list.size,
                modifier = Modifier.align(Alignment.Center),
                contentPadding = PaddingValues(horizontal = paddingValues),
                itemSpacing = paddingValues
            ) { page ->
                val wallpaper = list[page]

                AnimatedVisibility(
                    visible = !list.isNullOrEmpty()
                ) {
                    Card(
//                        elevation = elevation,
                        shape = shape,
                        backgroundColor = MaterialTheme.colors.primary,
                        modifier = Modifier
                            .height(width)
//                            .neumorphicPunched()
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onLongPress = { onLongPress(wallpaper) },
                                    onTap = { onWallpaperClick(wallpaper.id) },
                                )
                            }
                            .graphicsLayer {
                                val pageOffset =
                                    calculateCurrentOffsetForPage(page).absoluteValue

                                lerp(
                                    start = 0.85f,
                                    stop = 1f,
                                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                ).also { scale ->
                                    scaleX = scale
                                    scaleY = scale
                                }

                                alpha = lerp(
                                    start = 0.5f,
                                    stop = 1f,
                                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                )
                            }
                            .coloredShadow()
                    ) {
                        Box {
                            PexCoilImage(
                                imageUrl = wallpaper.imageUrlLandscape,
                                wallpaperId = wallpaper.id,
                                isSquare = true,
                                modifier = Modifier
                                    .fillMaxSize()
                            )
                            PexAnimatedHeart(
                                state = wallpaper.isFavorite,
                                size = 128.dp,
                                speed = 1.5f,
                                modifier = Modifier.align(Alignment.TopEnd)
                            )
                            Card(
                                modifier = Modifier
                                    .padding(all = paddingValues)
                                    .fillMaxSize(.5f)
                                    .align(Alignment.BottomStart),
                                shape = shape
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(MaterialTheme.colors.secondary)
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .align(Alignment.Center)
                                    ) {
                                        Text(
                                            text = stringResource(R.string.daily),
                                            fontSize = 24.sp,
                                            color = MaterialTheme.colors.onBackground
                                        )
                                        Text(
                                            text = stringResource(R.string.wallpaper),
                                            fontSize = 24.sp,
                                            color = MaterialTheme.colors.onBackground
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
private fun DailyShimmer(
    modifier: Modifier = Modifier,
    visible: Boolean = false,
    showErrorMessage: Boolean = false,
    message: String = "",
    width: Dp,
    elevation: Dp = 10.dp,
    shape: Shape = MaterialTheme.shapes.large,
    backgroundColor: Color = MaterialTheme.colors.primary,
    textColor: Color = MaterialTheme.colors.onPrimary
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(Modifier.fillMaxWidth()) {
            Card(
                elevation = elevation,
                shape = shape,
                modifier = modifier
                    .fillMaxWidth()
                    .height(width)
                    .align(Alignment.Center)
                    .shimmer()
                    .padding(horizontal = paddingValues)
                    .padding(bottom = paddingValues)
            ) {
                Box(modifier = Modifier.background(backgroundColor))
            }
            Card(
                modifier = Modifier
                    .padding(all = paddingValues)
                    .padding(start = paddingValues, bottom = paddingValues)
                    .size(width * 2 / 5)
                    .align(Alignment.BottomStart),
                shape = shape,
                elevation = elevation
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(PrimaryDark)
                ) {
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                    ) {
                        Text(
                            text = "Daily",
                            fontSize = 24.sp
                        )
                        Text(
                            text = "Wallpaper",
                            fontSize = 24.sp
                        )
                    }
                }
            }
            LoadingErrorText(
                visible = showErrorMessage,
                message = message,
                textColor = textColor,
                modifier = Modifier
                    .padding(paddingValues * 2)
                    .align(Alignment.TopCenter)
            )
        }
    }
}