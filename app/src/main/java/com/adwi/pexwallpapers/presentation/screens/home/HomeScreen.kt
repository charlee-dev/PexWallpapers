package com.adwi.pexwallpapers.presentation.screens.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.paging.ExperimentalPagingApi
import coil.annotation.ExperimentalCoilApi
import com.adwi.pexwallpapers.R
import com.adwi.pexwallpapers.presentation.components.*
import com.adwi.pexwallpapers.presentation.theme.Dimensions.BottomBar.BottomNavHeight
import com.adwi.pexwallpapers.presentation.theme.paddingValues
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@ExperimentalCoroutinesApi
@ExperimentalCoilApi
@ExperimentalMaterialApi
@ExperimentalPagingApi
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onWallpaperClick: (Int) -> Unit,
    onCategoryClick: (String) -> Unit,
    navigateToSearch: () -> Unit
) {
    val daily by viewModel.daily.collectAsState(null)
    val colors by viewModel.colors.collectAsState(null)
    val curated by viewModel.curated.collectAsState(null)

    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val pendingScrollToTopAfterRefresh by viewModel.pendingScrollToTopAfterRefresh.collectAsState()

    val homeListState = rememberLazyListState()
    val pagerState = rememberPagerState()
    val colorsListState = rememberLazyListState()
    val curatedListState = rememberLazyListState()

    val coroutineScope = rememberCoroutineScope()

    if (pendingScrollToTopAfterRefresh) {
        LaunchedEffect(pendingScrollToTopAfterRefresh) {
            coroutineScope.launch {
                homeListState.animateScrollToItem(0)
                pagerState.animateScrollToPage(0)
                colorsListState.animateScrollToItem(0)
                curatedListState.animateScrollToItem(0)
                viewModel.setPendingScrollToTopAfterRefresh(false)
            }
        }
    }

    PexScaffold(
        viewModel = viewModel
    ) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing),
            onRefresh = { viewModel.manualRefresh() }
        ) {
            LazyColumn(
                state = homeListState,
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(
                    bottom = BottomNavHeight + paddingValues
                )
            ) {
                item {
                    Header(
                        title = stringResource(id = R.string.home),
                        onActionClick = navigateToSearch,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = paddingValues)
                    )
                }
                item {
                    daily?.let { list ->
                        DailyWallpaper(
                            pagerState = pagerState,
                            modifier = Modifier
                                .padding(vertical = paddingValues / 2),
                            dailyList = list,
                            onWallpaperClick = { id -> onWallpaperClick(id) },
                            onLongPress = { viewModel.onFavoriteClick(it) }
                        )
                    }
                }
                item {
                    colors?.let { list ->
                        CategoryListHorizontalPanel(
                            panelTitle = stringResource(id = R.string.colors),
                            listState = colorsListState,
                            colors = list,
                            onCategoryClick = { onCategoryClick(it) }
                        )
                    }
                }
                item {
                    curated?.let { list ->
                        val categoryName = stringResource(id = R.string.curated)
                        WallpaperListHorizontalPanel(
                            panelName = categoryName,
                            wallpapers = list,
                            listState = curatedListState,
                            onWallpaperClick = { id -> onWallpaperClick(id) },
                            onLongPress = { viewModel.onFavoriteClick(it) },
                            onShowMoreClick = navigateToSearch
                        )
                    }
                }
            }
        }
    }
}
