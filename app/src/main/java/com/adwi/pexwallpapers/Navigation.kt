package com.adwi.pexwallpapers

import androidx.annotation.StringRes
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.*
import androidx.navigation.compose.composable
import coil.annotation.ExperimentalCoilApi
import com.adwi.home.HomeScreen
import com.adwi.home.HomeViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalCoilApi
fun NavGraphBuilder.myNavGraph(
    onWallpaperClick: (Long, NavBackStackEntry) -> Unit,
    onCategoryClick: () -> Unit,
    onSetWallpaperClick: (Long, NavBackStackEntry) -> Unit,
    upPress: () -> Unit
) {
    navigation(
        route = MainDestinations.HOME_ROUTE,
        startDestination = HomeSections.HOME.route
    ) {
        addHomeGraph(
            onWallpaperClick = onWallpaperClick,
            onCategoryClick = onCategoryClick
        )
    }

    composable(
        route = "${MainDestinations.WALLPAPER_PREVIEW_ROUTE}/{${MainDestinations.WALLPAPER_ID_KEY}}",
        arguments = listOf(navArgument(MainDestinations.WALLPAPER_ID_KEY) {
            type = NavType.IntType
        })
    ) { backStackEntry ->

//        val viewModel = hiltViewModel<PreviewViewModel>(backStackEntry)
        val arguments = requireNotNull(backStackEntry.arguments)
        val wallpaperId = arguments.getInt(MainDestinations.WALLPAPER_ID_KEY)

//        viewModel.getWallpaperById(wallpaperId)

//        PreviewScreen(
//            viewModel = viewModel,
//            onSetWallpaperClick = { id -> onSetWallpaperClick(id, backStackEntry) },
//            upPress = upPress
//        )
    }

    composable(
        route = "${MainDestinations.SET_WALLPAPER_ROUTE}/{${MainDestinations.WALLPAPER_ID_KEY}}",
        arguments = listOf(navArgument(MainDestinations.WALLPAPER_ID_KEY) {
            type = NavType.IntType
        })
    ) { backStackEntry ->

//        val viewModel = hiltViewModel<SetWallpaperViewModel>(backStackEntry)
        val arguments = requireNotNull(backStackEntry.arguments)
        val wallpaperId = arguments.getInt(MainDestinations.WALLPAPER_ID_KEY)

//        SetWallpaperScreen(
//            viewModel = viewModel,
//            wallpaperId = wallpaperId,
//            upPress = upPress
//        )
    }
}

@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalCoilApi
fun NavGraphBuilder.addHomeGraph(
    onWallpaperClick: (Long, NavBackStackEntry) -> Unit,
    onCategoryClick: () -> Unit
) {
    composable(HomeSections.HOME.route) { backStackEntry ->
        val viewModel = hiltViewModel<HomeViewModel>(backStackEntry)

//        viewModel.initData()

        HomeScreen(
            viewModel = viewModel,
            onWallpaperClick = { id -> onWallpaperClick(id, backStackEntry) },
            onCategoryClick = onCategoryClick
        )
    }
    composable(HomeSections.SEARCH.route) { backStackEntry ->
//        val viewModel = hiltViewModel<SearchViewModel>(backStackEntry)
//        SearchScreen(
//            viewModel = viewModel,
//            onWallpaperClick = { id -> onWallpaperClick(id, backStackEntry) }
//        )
    }
    composable(HomeSections.FAVORITES.route) { backStackEntry ->
//        val viewModel = hiltViewModel<FavoritesViewModel>(backStackEntry)
//        FavoritesScreen(
//            viewModel = viewModel,
//            onWallpaperClick = { id -> onWallpaperClick(id, backStackEntry) }
//        )
    }
    composable(HomeSections.SETTINGS.route) { backStackEntry ->
//        val viewModel = hiltViewModel<SettingsViewModel>(backStackEntry)
//        SettingsScreen(
//            viewModel = viewModel
//        )
    }
}

fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED

private val NavGraph.startDestination: NavDestination?
    get() = findNode(startDestinationId)

tailrec fun findStartDestination(graph: NavDestination): NavDestination {
    return if (graph is NavGraph) findStartDestination(graph.startDestination!!) else graph
}

enum class HomeSections(
    @StringRes val title: Int,
    val icon: ImageVector,
    val route: String
) {
    HOME(R.string.home, Icons.Outlined.Home, "home/home"),
    SEARCH(R.string.search, Icons.Outlined.Search, "home/search"),
    FAVORITES(R.string.favorites, Icons.Outlined.Bookmark, "home/bookmark"),
    SETTINGS(R.string.settings, Icons.Outlined.Settings, "home/settings")
}