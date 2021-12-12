package com.adwi.feature_home.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateInt
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import coil.annotation.ExperimentalCoilApi
import com.adwi.components.*
import com.adwi.components.R
import com.adwi.components.theme.PrimaryDark
import com.adwi.components.theme.paddingValues
import com.adwi.pexwallpapers.domain.model.Wallpaper
import com.google.accompanist.pager.*
import com.valentinilk.shimmer.shimmer
import kotlin.math.absoluteValue

@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalCoilApi
@Composable
fun DailyWallpaper(
    modifier: Modifier = Modifier,
    scrollState: ScrollState,
    pagerState: PagerState = rememberPagerState(),
    dailyList: com.adwi.core.DataState<List<Wallpaper>>,
    shape: Shape = MaterialTheme.shapes.large,
    onWallpaperClick: (Int) -> Unit,
    onLongPress: (Wallpaper) -> Unit,
    lowRes: Boolean,
    showShadows: Boolean,
    showParallax: Boolean
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
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                HorizontalPager(
                    state = pagerState,
                    count = list.size,
                    modifier = Modifier,
                    contentPadding = PaddingValues(horizontal = paddingValues),
                    itemSpacing = paddingValues
                ) { page ->

                    val wallpaper = list[page]

                    var isPressed by remember { mutableStateOf(false) }
                    val pressed = updateTransition(targetState = isPressed, label = "Press")

                    val pressedScale by pressed.animateFloat(label = "Scale") {
                        if (it) .98f else 1f
                    }
                    val textWeight by pressed.animateInt(
                        label = "Weight",
                        transitionSpec = { tween(durationMillis = 1000) }
                    ) { state ->
                        if (state) 650 else 450
                    }
                    val textSpacing by pressed.animateFloat(
                        label = "Letter spacing",
                        transitionSpec = { tween(durationMillis = 500) }
                    ) { state ->
                        if (state) 1f else 1.2f
                    }

                    AnimatedVisibility(
                        visible = list.isNotEmpty()
                    ) {
                        Card(
                            shape = shape,
                            backgroundColor = MaterialTheme.colors.primary,
                            modifier = Modifier
                                .height(width)
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onLongPress = { onLongPress(wallpaper) },
                                        onTap = { onWallpaperClick(wallpaper.id) },
                                        onPress = {
                                            isPressed = true
                                            this.tryAwaitRelease()
                                            isPressed = false
                                        }
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
                                        scaleX =
                                            if (pagerState.isScrollInProgress) scale else pressedScale
                                        scaleY =
                                            if (pagerState.isScrollInProgress) scale else pressedScale
                                    }
                                    alpha = lerp(
                                        start = 0.5f,
                                        stop = 1f,
                                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                    )
                                }
                                .neumorphicShadow(enabled = showShadows)
                        ) {
                            Box {
                                PexCoilImage(
                                    imageUrl = if (lowRes) wallpaper.imageUrlTiny else wallpaper.imageUrlLandscape,
                                    wallpaperId = wallpaper.id,
                                    isSquare = true,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .graphicsLayer {
//                                            if (showParallax) {
                                            val scale = 1.1f
                                            scaleY = scale
                                            scaleX = scale
                                            translationY = scrollState.value * 0.15f
//                                            }
                                        }
                                )
                                PexAnimatedHeart(
                                    isFavorite = wallpaper.isFavorite,
                                    size = 32.dp,
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .padding(paddingValues)
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
                                                color = MaterialTheme.colors.onBackground,
                                                fontWeight = FontWeight(textWeight)
                                            )
                                            Text(
                                                text = stringResource(R.string.wallpaper),
                                                fontSize = 24.sp,
                                                color = MaterialTheme.colors.onBackground,
                                                fontWeight = FontWeight(textWeight),
                                                letterSpacing = textSpacing.sp
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                HorizontalPagerIndicator(
                    pagerState = pagerState,
                    modifier = Modifier
                        .padding(top = paddingValues)
                        .padding(horizontal = paddingValues),
                    activeColor = if (!isSystemInDarkTheme())
                        MaterialTheme.colors.primary else MaterialTheme.colors.primaryVariant,
                    inactiveColor = MaterialTheme.colors.secondary
                )
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