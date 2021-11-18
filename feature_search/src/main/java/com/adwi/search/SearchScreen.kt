package com.adwi.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
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
import com.adwi.components.PexAnimatedHeart
import com.adwi.components.PexCoilImage
import com.adwi.components.PexSearchToolbar
import com.adwi.components.theme.paddingValues
import com.adwi.datasource.local.domain.WallpaperEntity
import com.adwi.datasource.local.domain.toDomain
import com.adwi.domain.Wallpaper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import timber.log.Timber

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

    val query = remember { mutableStateOf("") }

    val pendingScrollToTop = remember { mutableStateOf(viewModel.pendingScrollToTopAfterRefresh) }

    val state = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    if (pendingScrollToTop.value) {
        LaunchedEffect(true) {
            coroutineScope.launch {
                state.animateScrollToItem(0)
            }
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        WallpaperListPaged(
            modifier = Modifier.fillMaxSize(),
            wallpapers = wallpapers,
            onWallpaperClick = onWallpaperClick,
            onLongPress = { wallpaper ->
                Timber.tag("HomeScreen").d("${wallpaper.id} - long")
                viewModel.onTriggerEvent(SearchEvents.OnFavoriteClick(wallpaper))
            }
        )
        PexSearchToolbar(
            query = query.value,
            onHeroNameChanged = { newQuery ->
                query.value = newQuery
            },
            onExecuteSearch = { viewModel.onTriggerEvent(SearchEvents.OnSearchQuerySubmit(query.value)) },
            onShowFilterDialog = {},
            modifier = Modifier
                .padding(horizontal = paddingValues)
                .padding(vertical = paddingValues / 2)
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        )

    }
}

@ExperimentalMaterialApi
@ExperimentalCoilApi
@ExperimentalFoundationApi
@Composable
fun WallpaperListPaged(
    modifier: Modifier = Modifier,
    wallpapers: Flow<PagingData<WallpaperEntity>>,
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
                var isHeartEnabled by remember { mutableStateOf(wallpaper.isFavorite) }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = paddingValues)
                        .height((wallpaper.height / 2.5).dp)
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onTap = { onWallpaperClick(wallpaper.id) },
                                onLongPress = {
                                    isHeartEnabled = !isHeartEnabled
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
                            state = isHeartEnabled,
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