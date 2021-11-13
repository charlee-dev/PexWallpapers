package com.adwi.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.adwi.components.PexCoilImage
import com.adwi.components.PexSearchToolbar
import com.adwi.components.theme.paddingValues
import com.adwi.datasource.local.domain.WallpaperEntity
import com.adwi.datasource.local.domain.toDomain
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@ExperimentalComposeUiApi
@ExperimentalCoroutinesApi
@ExperimentalCoilApi
@ExperimentalMaterialApi
@ExperimentalPagingApi
@ExperimentalFoundationApi
@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    onSearchClick: () -> Unit,
    onWallpaperClick: (Int) -> Unit
) {
    val wallpapers = viewModel.searchResults

    val query = remember { mutableStateOf("") }

    Column {
        PexSearchToolbar(
            query = query.value,
            onHeroNameChanged = { newQuery ->
                query.value = newQuery
            },
            onExecuteSearch = { viewModel.onSearchQuerySubmit(query.value) },
            onShowFilterDialog = {},
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
        )
        WallpaperListPaged(
            wallpapers = wallpapers,
            onWallpaperClick = onWallpaperClick
        )
    }
}

@ExperimentalMaterialApi
@ExperimentalCoilApi
@ExperimentalFoundationApi
@Composable
fun WallpaperListPaged(
    wallpapers: Flow<PagingData<WallpaperEntity>>,
    onWallpaperClick: (Int) -> Unit
) {
    val lazyWallpaperItems = wallpapers.collectAsLazyPagingItems()
    LazyColumn(
        contentPadding = PaddingValues(
//        start = paddingValues,
//        end = paddingValues,
            top = paddingValues,
            bottom = paddingValues * 3
        ),
        verticalArrangement = Arrangement.spacedBy(paddingValues / 2)
    ) {
        items(lazyWallpaperItems.itemCount) { index ->
            lazyWallpaperItems[index]?.let {
                val wallpaper = it.toDomain()
                Card(
                    onClick = { onWallpaperClick(it.id) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = paddingValues)
                        .height((wallpaper.height / 2).dp),
                    elevation = 10.dp,
                    shape = MaterialTheme.shapes.large
                ) {
                    PexCoilImage(imageUrl = wallpaper.imageUrlLandscape)
                }
            }
        }
    }
}