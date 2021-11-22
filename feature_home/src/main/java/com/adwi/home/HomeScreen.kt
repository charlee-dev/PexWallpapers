package com.adwi.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.paging.ExperimentalPagingApi
import coil.annotation.ExperimentalCoilApi
import com.adwi.components.*
import com.adwi.components.theme.Dimensions.BottomBar.BottomNavHeight
import com.adwi.components.theme.paddingValues
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber

@ExperimentalPagerApi
@ExperimentalCoroutinesApi
@ExperimentalCoilApi
@ExperimentalMaterialApi
@ExperimentalPagingApi
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onTriggerEvent: (HomeEvent) -> Unit,
    onWallpaperClick: (Int) -> Unit,
    onCategoryClick: () -> Unit
) {
    val curated by viewModel.curatedList.collectAsState()
    val colors by viewModel.colorList.collectAsState()
    val dailys by viewModel.dailyList.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()

    val homeListState = rememberLazyListState()
    val scaffoldState = rememberScaffoldState()

    PexScaffold(viewModel = viewModel, scaffoldState = scaffoldState) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing),
            onRefresh = { onTriggerEvent(HomeEvent.ManualRefresh) }
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
                        onActionClick = onCategoryClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = paddingValues)
                    )
                }
                item {
                    DailyWallpaper(
                        modifier = Modifier
                            .padding(vertical = paddingValues / 2),
                        dailyList = dailys,
                        onWallpaperClick = { id -> onWallpaperClick(id) },
                        onLongPress = { wallpaper ->
                            onTriggerEvent(HomeEvent.OnFavoriteClick(wallpaper))
                        }
                    )
                }
                item {
                    CategoryListHorizontalPanel(
                        panelTitle = stringResource(id = R.string.colors),
                        colors = colors,
                        onCategoryClick = { categoryName ->
                            onTriggerEvent(HomeEvent.SetCategory(categoryName))
                            onCategoryClick()
                        }
                    )
                }
                item {
                    val categoryName = stringResource(id = R.string.curated)
                    WallpaperListHorizontalPanel(
                        categoryName = categoryName,
                        wallpapers = curated,
                        onWallpaperClick = { id -> onWallpaperClick(id) },
                        onLongPress = { wallpaper ->
                            Timber.tag("HomeScreen").d("${wallpaper.id} - long")
                            onTriggerEvent(HomeEvent.OnFavoriteClick(wallpaper))
                        },
                        onShowMoreClick = {
                            onTriggerEvent(HomeEvent.SetCategory(categoryName))
                            onCategoryClick()
                        }
                    )
                }
            }
        }
    }
}
