package com.adwi.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import coil.annotation.ExperimentalCoilApi
import com.adwi.components.CategoryListPanel
import com.adwi.components.DailyWallpaper
import com.adwi.components.Header
import com.adwi.components.WallpaperListPanel
import com.adwi.components.theme.Dimensions.BottomBar.BottomNavHeight
import com.adwi.components.theme.paddingValues

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onWallpaperClick: (Long) -> Unit,
    onCategoryClick: () -> Unit
) {
    val state by viewModel.state
    val dailyWallpaper by viewModel.dailyWallpaper.collectAsState()
    val colorList by viewModel.colorList.collectAsState()
    val curatedWallpapers by viewModel.wallpaperList.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(
            bottom = BottomNavHeight + paddingValues
        )
    ) {
        item {
            Header()
        }
        item {
            DailyWallpaper(
                modifier = Modifier
                    .padding(horizontal = paddingValues, vertical = paddingValues / 2),
                wallpaper = dailyWallpaper,
                onWallpaperClick = { id -> onWallpaperClick(id) }
            )
        }
        item {
            CategoryListPanel(
                categoryName = stringResource(id = R.string.colors),
                list = colorList,
                onCategoryClick = { name ->
//                    viewModel.setCategoryName(name)
                    onCategoryClick()
                }
            )
        }
        item {
            WallpaperListPanel(
                categoryName = stringResource(id = R.string.curated),
                state = state,
                onWallpaperClick = { id -> onWallpaperClick(id) }
            )
        }
    }
}