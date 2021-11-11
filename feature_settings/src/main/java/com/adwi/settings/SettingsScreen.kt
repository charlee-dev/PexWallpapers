package com.adwi.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import coil.annotation.ExperimentalCoilApi
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun SettingsScreen(
//    viewModel: HomeViewModel,
//    onWallpaperClick: (Int) -> Unit,
//    onCategoryClick: () -> Unit,
) {
    Column() {
        Text("SettingsScreen")
    }
//    val dailyState = viewModel.dailyState.value
//    val colorsState = viewModel.colorsState.value
//    val curatedState = viewModel.curatedState.value
//
//    LazyColumn(
//        modifier = Modifier
//            .fillMaxSize(),
//        contentPadding = PaddingValues(
//            bottom = BottomNavHeight + paddingValues
//        )
//    ) {
//        item {
//            Header()
//        }
//        item {
//            DailyWallpaper(
//                modifier = Modifier
//                    .padding(horizontal = paddingValues, vertical = paddingValues / 2),
//                state = dailyState,
//                onWallpaperClick = { id -> onWallpaperClick(id) }
//            )
//        }
//        item {
//            CategoryListPanel(
//                categoryName = stringResource(id = R.string.colors),
//                state = colorsState,
//                onCategoryClick = { name ->
////                    viewModel.setCategoryName(name)
//                    onCategoryClick()
//                }
//            )
//        }
//        item {
//            WallpaperListPanel(
//                categoryName = stringResource(id = R.string.curated),
//                state = curatedState,
//                onWallpaperClick = { id -> onWallpaperClick(id) }
//            )
//        }
//    }
}
