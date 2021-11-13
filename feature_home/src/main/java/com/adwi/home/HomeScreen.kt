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

@ExperimentalCoroutinesApi
@ExperimentalCoilApi
@ExperimentalMaterialApi
@ExperimentalPagingApi
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onWallpaperClick: (Int) -> Unit,
    onCategoryClick: () -> Unit
) {
    val dailyState = viewModel.dailyState.value
    val colorsState = viewModel.colorsState.value
    val curatedState = viewModel.curatedState.value

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
                modifier = Modifier.fillMaxWidth()
            )
        }
        item {
            DailyWallpaper(
                modifier = Modifier
                    .padding(horizontal = paddingValues, vertical = paddingValues / 2),
                state = dailyState,
                onWallpaperClick = { id -> onWallpaperClick(id) }
            )
        }
        item {
            CategoryListPanel(
                categoryName = stringResource(id = R.string.colors),
                state = colorsState,
                onCategoryClick = { name ->
//                    viewModel.setCategoryName(name)
                    onCategoryClick()
                }
            )
        }
        item {
            WallpaperListPanel(
                categoryName = stringResource(id = R.string.curated),
                state = curatedState,
                onWallpaperClick = { id -> onWallpaperClick(id) }
            )
        }
    }
}
