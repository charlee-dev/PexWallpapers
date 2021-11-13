package com.adwi.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.paging.ExperimentalPagingApi
import coil.annotation.ExperimentalCoilApi
import com.adwi.components.Header
import com.adwi.components.WallpaperListPanel
import com.adwi.components.theme.Dimensions.BottomBar.BottomNavHeight
import com.adwi.components.theme.paddingValues
import com.adwi.home.SearchViewModel
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
    Column {
        Header(
            modifier = Modifier,
            title = "Search",
            onSearchClick = onSearchClick
        )
    }
    val curatedState = viewModel.curatedState.value

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(
            bottom = BottomNavHeight + paddingValues
        )
    ) {
        items(curatedState.wallpapers) {
            WallpaperListPanel(
                categoryName = stringResource(id = R.string.curated),
                state = curatedState,
                onWallpaperClick = { id -> onWallpaperClick(id) }
            )
        }
    }
}
