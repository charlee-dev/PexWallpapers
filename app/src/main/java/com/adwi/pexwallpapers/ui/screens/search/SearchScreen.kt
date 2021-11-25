package com.adwi.pexwallpapers.ui.screens.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.adwi.pexwallpapers.components.PexAnimatedHeart
import com.adwi.pexwallpapers.components.PexCoilImage
import com.adwi.pexwallpapers.components.PexScaffold
import com.adwi.pexwallpapers.components.PexSearchToolbar
import com.adwi.pexwallpapers.data.wallpapers.database.domain.toDomain
import com.adwi.pexwallpapers.model.Wallpaper
import com.adwi.pexwallpapers.ui.theme.paddingValues
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

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
    val wallpapers = viewModel.searchResults

    val currentQuery by viewModel.currentQuery.collectAsState()

    val query = remember { mutableStateOf("") }

    val pendingScrollToTop =
        remember { mutableStateOf(viewModel.pendingScrollToTopAfterRefresh.value) }

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    if (pendingScrollToTop.value) {
        LaunchedEffect(true) {
            coroutineScope.launch {
                listState.animateScrollToItem(0)
            }
        }
    }

    PexScaffold(
        viewModel = viewModel,
        scaffoldState = scaffoldState
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            val showRefresh = false
            val swipeRefreshState = rememberSwipeRefreshState(showRefresh)

            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = {
//                            onRefresh TODO(add onRefresh in viewModel)
                },
            ) {
                WallpaperListPaged(
                    modifier = Modifier.fillMaxSize(),
                    wallpapers = wallpapers,
                    onWallpaperClick = onWallpaperClick,
                    onLongPress = { viewModel.onFavoriteClick(it) }
                )
            }
            PexSearchToolbar(
                query = query.value,
                onQueryChanged = { query.value = it },
                onExecuteSearch = { viewModel.onSearchQuerySubmit(query.value) },
                onShowFilterDialog = {},
                modifier = Modifier
                    .padding(horizontal = paddingValues)
                    .padding(vertical = paddingValues / 2)
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
            )
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalCoilApi
@ExperimentalFoundationApi
@Composable
fun WallpaperListPaged(
    modifier: Modifier = Modifier,
    wallpapers: Flow<PagingData<com.adwi.pexwallpapers.data.wallpapers.database.domain.WallpaperEntity>>,
    onWallpaperClick: (Int) -> Unit,
    onLongPress: (Wallpaper) -> Unit,
    state: LazyListState = rememberLazyListState()
) {
    val lazyWallpaperItems = wallpapers.collectAsLazyPagingItems()

    LazyColumn(
        modifier = modifier,
        state = state,
        contentPadding = PaddingValues(
            top = paddingValues * 4,
            bottom = paddingValues * 3
        ),
        verticalArrangement = Arrangement.spacedBy(paddingValues / 2)
    ) {
        items(lazyWallpaperItems.itemCount) { index ->
            lazyWallpaperItems[index]?.let {
                val wallpaper = it.toDomain()
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = paddingValues)
                        .height((wallpaper.height / 2.5).dp)
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onTap = { onWallpaperClick(wallpaper.id) },
                                onLongPress = {
                                    onLongPress(wallpaper)
                                }
                            )
                        },
                    elevation = 10.dp,
                    shape = MaterialTheme.shapes.large
                ) {
                    Box() {
                        PexCoilImage(
                            imageUrl = wallpaper.imageUrlPortrait,
                            modifier = Modifier.fillMaxSize()
                        )
                        PexAnimatedHeart(
                            state = wallpaper.isFavorite,
                            size = 64.dp,
                            speed = 1.5f,
                            modifier = Modifier.align(Alignment.TopEnd)
                        )
                    }
                }
            }
        }
    }
}