package com.adwi.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.paging.ExperimentalPagingApi
import coil.annotation.ExperimentalCoilApi
import com.adwi.components.CategoryListHorizontalPanel
import com.adwi.components.DailyWallpaper
import com.adwi.components.Header
import com.adwi.components.WallpaperListHorizontalPanel
import com.adwi.components.theme.Dimensions.BottomBar.BottomNavHeight
import com.adwi.components.theme.paddingValues
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber

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
    val daily by viewModel.dailyWallpaper.collectAsState()
    val colors by viewModel.colorList.collectAsState()
    val curated by viewModel.curatedList.collectAsState()

    LazyColumn(
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
                    .padding(horizontal = paddingValues, vertical = paddingValues / 2),
                wallpaper = daily,
                onWallpaperClick = { id -> onWallpaperClick(id) },
                onLongPress = { id ->
                    onTriggerEvent(HomeEvent.OnFavoriteClick(daily))
                }
            )
        }
        item {
            CategoryListHorizontalPanel(
                categoryName = stringResource(id = R.string.colors),
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
