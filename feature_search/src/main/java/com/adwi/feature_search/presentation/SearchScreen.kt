package com.adwi.feature_search.presentation

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.adwi.components.*
import com.adwi.components.theme.MenuItems
import com.adwi.components.theme.paddingValues
import com.adwi.data.database.domain.toDomain
import com.adwi.feature_search.presentation.components.NothingHereYetMessage
import com.adwi.feature_search.presentation.components.PexSearchToolbar
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalComposeUiApi
@ExperimentalCoroutinesApi
@ExperimentalCoilApi
@ExperimentalMaterialApi
@ExperimentalPagingApi
@ExperimentalFoundationApi
@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    onWallpaperClick: (Int) -> Unit,
    onGiveFeedbackClick: () -> Unit,
    onRequestFeature: () -> Unit,
    onReportBugClick: () -> Unit
) {
    val wallpapers = viewModel.searchResults.collectAsLazyPagingItems()
    val currentQuery by viewModel.currentQuery.collectAsState()
    val pendingScrollToTopAfterRefresh by viewModel.pendingScrollToTopAfterRefresh.collectAsState()

    val listState = rememberLazyListState()
    val swipeRefreshState =
        rememberSwipeRefreshState(wallpapers.loadState.refresh is LoadState.Loading)

    LaunchedEffect(pendingScrollToTopAfterRefresh && wallpapers.loadState.refresh != LoadState.Loading) {
        listState.animateScrollToItem(0)
        viewModel.setPendingScrollToTopAfterRefresh(false)
    }

    PexScaffold(
        viewModel = viewModel,
    ) {
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = { viewModel.onSearchQuerySubmit(currentQuery) },
            indicatorPadding = PaddingValues(top = paddingValues * 4)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                NothingHereYetMessage(
                    modifier = Modifier.align(Alignment.Center),
                    visible = wallpapers.itemCount == 0
                )
            }
            LazyColumn(
                modifier = Modifier,
                state = listState,
                contentPadding = PaddingValues(
                    bottom = paddingValues * 3
                ),
                verticalArrangement = Arrangement.spacedBy(paddingValues / 2)
            ) {
                item {
                    PexSearchToolbar(
                        query = currentQuery,
                        onQueryChanged = { viewModel.onSearchQuerySubmit(it) },
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
                items(wallpapers.itemCount) { index ->
                    wallpapers[index]?.let {

                        var isPressed by remember { mutableStateOf(false) }
                        val pressed = updateTransition(targetState = isPressed, label = "Press")

                        val scale by pressed.animateFloat(
                            label = "Scale",
                            transitionSpec = { tween(400) }
                        ) {
                            if (it) .98f else 1f
                        }
                        val wallpaper = it.toDomain()

                        Card(
                            shape = MaterialTheme.shapes.large,
                            backgroundColor = MaterialTheme.colors.primary,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = paddingValues)
                                .height((wallpaper.height / 2.5).dp)
                                .neumorphicShadow(
                                    enabled = viewModel.showShadows
                                )
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onTap = { onWallpaperClick(wallpaper.id) },
                                        onLongPress = {
                                            viewModel.onFavoriteClick(wallpaper)
                                        },
                                        onPress = {
                                            isPressed = true
                                            this.tryAwaitRelease()
                                            isPressed = false
                                        }
                                    )
                                }
                                .graphicsLayer {
                                    scaleX = scale
                                    scaleY = scale
                                }
                        ) {
                            Box {
                                PexCoilImage(
                                    imageUrl = if (viewModel.lowRes)
                                        wallpaper.imageUrlTiny else wallpaper.imageUrlPortrait,
                                    modifier = Modifier.fillMaxSize()
                                )
                                PexAnimatedHeart(
                                    isFavorite = wallpaper.isFavorite,
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .padding(paddingValues)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}