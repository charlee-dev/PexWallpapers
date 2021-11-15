package com.adwi.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.paging.ExperimentalPagingApi
import coil.annotation.ExperimentalCoilApi
import com.adwi.components.CategoryListPanel
import com.adwi.components.DailyWallpaper
import com.adwi.components.Header
import com.adwi.components.WallpaperListPanel
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
    state: HomeState,
    onTriggerEvent: (HomeEvent) -> Unit,
    onWallpaperClick: (Int) -> Unit,
    onCategoryClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(
            bottom = BottomNavHeight + paddingValues
        )
    ) {
        item {
            Header(
                onSearchClick = onCategoryClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = paddingValues)
            )
        }
        item {
            DailyWallpaper(
                modifier = Modifier
                    .padding(horizontal = paddingValues, vertical = paddingValues / 2),
                state = state.daily.value,
                onWallpaperClick = { id -> onWallpaperClick(id) },
                onLongPress = { id ->
                    onTriggerEvent(HomeEvent.OnFavoriteClick(state.daily.value.wallpaper))
                }
            )
        }
        item {
            CategoryListPanel(
                categoryName = stringResource(id = R.string.colors),
                state = state.colors.value,
                onCategoryClick = { categoryName ->
                    onTriggerEvent(HomeEvent.SetCategory(categoryName))
                    onCategoryClick()
                }
            )
        }
        item {
            WallpaperListPanel(
                categoryName = stringResource(id = R.string.curated),
                state = state.curated.value,
                onWallpaperClick = { id -> onWallpaperClick(id) },
                onLongPress = { wallpaper ->
                    Timber.tag("HomeScreen").d("${wallpaper.id} - long")
                    onTriggerEvent(HomeEvent.OnFavoriteClick(wallpaper))
                }
            )
        }
    }
}
