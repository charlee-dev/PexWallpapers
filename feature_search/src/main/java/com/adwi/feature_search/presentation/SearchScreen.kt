package com.adwi.feature_search.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.adwi.components.PexScaffold
import com.adwi.feature_search.presentation.components.NothingHereYetMessage
import com.adwi.feature_search.presentation.components.PexSearchToolbar
import com.adwi.feature_search.presentation.components.WallpaperListPaged
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import dagger.Lazy
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
    onWallpaperClick: (Int) -> Unit
) {
    val wallpapers = viewModel.searchResults.collectAsLazyPagingItems()
    val currentQuery by viewModel.currentQuery.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val pendingScrollToTopAfterRefresh by viewModel.pendingScrollToTopAfterRefresh.collectAsState()

    if (wallpapers.loadState.refresh == LoadState.Loading) {
        viewModel.setIsRefreshing(true)
    }

    val listState = rememberLazyListState()
    val scaffoldState = rememberScaffoldState()

    val swipeRefreshState =
        rememberSwipeRefreshState(isRefreshing && wallpapers.itemCount == 0)

    LaunchedEffect(pendingScrollToTopAfterRefresh && wallpapers.loadState.refresh != LoadState.Loading) {
        listState.animateScrollToItem(0)
        viewModel.setPendingScrollToTopAfterRefresh(false)
    }

    PexScaffold(
        viewModel = viewModel,
        scaffoldState = scaffoldState
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                PexSearchToolbar(
                    query = currentQuery,
                    onQueryChanged = { viewModel.onSearchQuerySubmit(it) },
                    onShowFilterDialog = {},
                    showShadows = viewModel.showShadows
                )
            }
            item {
                SwipeRefresh(
                    state = swipeRefreshState,
                    onRefresh = { viewModel.onSearchQuerySubmit(currentQuery) },
                ) {
                    AnimatedVisibility(
                        visible = wallpapers.itemCount > 0,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        WallpaperListPaged(
                            modifier = Modifier.fillMaxSize(),
                            wallpapers = wallpapers,
                            listState = listState,
                            onWallpaperClick = onWallpaperClick,
                            onLongPress = { viewModel.onFavoriteClick(it) },
                            lowRes = viewModel.lowRes,
                            showShadows = viewModel.showShadows
                        )
                    }
                }
            }
            item { NothingHereYetMessage(visible = currentQuery.isEmpty()) }
        }
    }
}