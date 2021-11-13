package com.adwi.search

import androidx.compose.foundation.layout.Column
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.paging.ExperimentalPagingApi
import coil.annotation.ExperimentalCoilApi
import com.adwi.components.Header
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalCoilApi
@ExperimentalMaterialApi
@ExperimentalPagingApi
@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    onSearchClick: () -> Unit,
    onWallpaperClick: (Int) -> Unit
) {
    val query by viewModel.currentQuery.collectAsState()

    Column {
        Header(
            modifier = Modifier,
            title = "Search",
            onSearchClick = onSearchClick
        )
        query?.let {
            Text(text = it)
        }
    }

//    LazyColumn(
//        modifier = Modifier
//            .fillMaxSize(),
//        contentPadding = PaddingValues(
//            bottom = BottomNavHeight + paddingValues
//        )
//    ) {
//
//        items(curatedState.wallpapers) {
//            WallpaperListPanel(
//                categoryName = stringResource(id = R.string.curated),
//                state = curatedState,
//                onWallpaperClick = { id -> onWallpaperClick(id) }
//            )
//        }
//    }
}
