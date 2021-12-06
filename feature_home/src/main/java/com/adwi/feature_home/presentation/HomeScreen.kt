package com.adwi.feature_home.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.paging.ExperimentalPagingApi
import coil.annotation.ExperimentalCoilApi
import com.adwi.components.Header
import com.adwi.components.PexScaffold
import com.adwi.components.theme.Dimensions.BottomBar.BottomNavHeight
import com.adwi.components.theme.paddingValues
import com.adwi.feature_home.R
import com.adwi.feature_home.presentation.components.CategoryListHorizontalPanel
import com.adwi.feature_home.presentation.components.DailyWallpaper
import com.adwi.feature_home.presentation.components.WallpaperListHorizontalPanel
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
    viewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onWallpaperClick: (Int) -> Unit,
    onCategoryClick: (String) -> Unit,
    navigateToSearch: () -> Unit
) {
    val daily by viewModel.daily.collectAsState()
    val colors by viewModel.colors.collectAsState()
    val curated by viewModel.curated.collectAsState()
    val lowRes = viewModel.lowRes

    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val pendingScrollToTopAfterRefresh by viewModel.pendingScrollToTopAfterRefresh.collectAsState()

    val homeListState = rememberScrollState()
    val pagerState = rememberPagerState()
    val colorsListState = rememberLazyListState()
    val curatedListState = rememberLazyListState()

    val coroutineScope = rememberCoroutineScope()

    if (pendingScrollToTopAfterRefresh) {
        LaunchedEffect(pendingScrollToTopAfterRefresh) {
            coroutineScope.launch {
                homeListState.animateScrollTo(0)
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(state = homeListState)
                    .padding(
                        bottom = BottomNavHeight + paddingValues
                    )
            ) {
                Header(
                    title = stringResource(id = R.string.home),
                    onActionClick = navigateToSearch
                )
                daily?.let { list ->
                    DailyWallpaper(
                        scrollState = homeListState,
                        pagerState = pagerState,
                        modifier = Modifier
                            .padding(vertical = paddingValues / 2),
                        dailyList = list,
                        onWallpaperClick = { id -> onWallpaperClick(id) },
                        onLongPress = { viewModel.onFavoriteClick(it) },
                        lowRes = lowRes
                    )
                }
                CategoryListHorizontalPanel(
                    panelTitle = stringResource(id = R.string.colors),
                    verticalScrollState = homeListState,
                    listState = colorsListState,
                    colors = colors,
                    onCategoryClick = { onCategoryClick(it) }
                )
                curated?.let { list ->
                    WallpaperListHorizontalPanel(
                        panelName = stringResource(id = R.string.curated),
                        verticalScrollState = homeListState,
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