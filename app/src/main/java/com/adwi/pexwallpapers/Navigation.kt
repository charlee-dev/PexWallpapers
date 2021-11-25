package com.adwi.pexwallpapers

import androidx.annotation.StringRes
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.*
import androidx.paging.ExperimentalPagingApi
import coil.annotation.ExperimentalCoilApi
import com.adwi.favorites.FavoritesScreen
import com.adwi.favorites.FavoritesViewModel
import com.adwi.home.HomeScreen
import com.adwi.home.HomeViewModel
import com.adwi.home.SettingsViewModel
import com.adwi.preview.PreviewScreen
import com.adwi.preview.PreviewViewModel
import com.adwi.search.SearchScreen
import com.adwi.search.SearchViewModel
import com.adwi.settings.SettingsScreen
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalPermissionsApi
@InternalCoroutinesApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalCoilApi
@ExperimentalPagingApi
@ExperimentalFoundationApi
fun NavGraphBuilder.myNavGraph(
    onWallpaperClick: (Int, NavBackStackEntry) -> Unit,
    onCategoryClick: (String, NavBackStackEntry) -> Unit,
    onSetWallpaperClick: (Int, NavBackStackEntry) -> Unit,
    navigateToSearch: () -> Unit,
    upPress: () -> Unit
) {
    navigation(
        route = MainDestinations.HOME_ROUTE,
        startDestination = HomeSections.HOME.route
    ) {
        addHomeGraph(
            onWallpaperClick = onWallpaperClick,
            onCategoryClick = onCategoryClick,
            navigateToSearch = navigateToSearch
        )
    }

    composable(
        route = "${MainDestinations.WALLPAPER_PREVIEW_ROUTE}/{${MainDestinations.WALLPAPER_ID_KEY}}",
        arguments = listOf(navArgument(MainDestinations.WALLPAPER_ID_KEY) {
            type = NavType.IntType
        })
    ) { backStackEntry ->
        val viewModel = hiltViewModel<PreviewViewModel>(backStackEntry)
        PreviewScreen(
            viewModel = viewModel,
//            onSetWallpaperClick = { id -> onSetWallpaperClick(id, backStackEntry) },
            upPress = upPress
        )
    }

    composable(
        route = "${MainDestinations.SEARCH_ROUTE}/{${MainDestinations.SEARCH_QUERY}}",
        arguments = listOf(navArgument(MainDestinations.SEARCH_QUERY) {
            type = NavType.StringType
        })
    ) { backStackEntry ->
        val viewModel = hiltViewModel<SearchViewModel>(backStackEntry)

        viewModel.restoreSavedQuery()

        SearchScreen(
            viewModel = viewModel,
            onWallpaperClick = { id -> onWallpaperClick(id, backStackEntry) }
        )
    }

//    composable(
//        route = "${MainDestinations.SET_WALLPAPER_ROUTE}/{${MainDestinations.WALLPAPER_ID_KEY}}",
//        arguments = listOf(navArgument(MainDestinations.WALLPAPER_ID_KEY) {
//            type = NavType.IntType
//        })
//    ) { backStackEntry ->

//        val viewModel = hiltViewModel<SetWallpaperViewModel>(backStackEntry)
//        val arguments = requireNotNull(backStackEntry.arguments)
//        val wallpaperId = arguments.getInt(MainDestinations.WALLPAPER_ID_KEY)

//        SetWallpaperScreen(
//            viewModel = viewModel,
//            wallpaperId = wallpaperId,
//            upPress = upPress
//        )
//    }
}

@ExperimentalPermissionsApi
@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalCoilApi
@ExperimentalPagingApi
@ExperimentalFoundationApi
@ExperimentalComposeUiApi
fun NavGraphBuilder.addHomeGraph(
    onWallpaperClick: (Int, NavBackStackEntry) -> Unit,
    onCategoryClick: (String, NavBackStackEntry) -> Unit,
    navigateToSearch: () -> Unit
) {
    composable(HomeSections.HOME.route) { backStackEntry ->
        val viewModel = hiltViewModel<HomeViewModel>(backStackEntry)
        viewModel.onStart()
        HomeScreen(
            viewModel = viewModel,
            onWallpaperClick = { id -> onWallpaperClick(id, backStackEntry) },
            onCategoryClick = { query -> onCategoryClick(query, backStackEntry) },
            navigateToSearch = navigateToSearch
        )
    }
    composable(HomeSections.SEARCH.route) { backStackEntry ->
        val viewModel = hiltViewModel<SearchViewModel>(backStackEntry)

        viewModel.restoreSavedQuery()

        SearchScreen(
            viewModel = viewModel,
            onWallpaperClick = { id -> onWallpaperClick(id, backStackEntry) }
        )
    }
    composable(HomeSections.FAVORITES.route) { backStackEntry ->
        val viewModel = hiltViewModel<FavoritesViewModel>(backStackEntry)
        viewModel.getFavorites()
        FavoritesScreen(
            viewModel = viewModel,
            onSearchClick = navigateToSearch,
            onWallpaperClick = { id -> onWallpaperClick(id, backStackEntry) }
        )
    }
    composable(HomeSections.SETTINGS.route) { backStackEntry ->
        val viewModel = hiltViewModel<SettingsViewModel>(backStackEntry)

        SettingsScreen(
            viewModel = viewModel,
        )
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
    FAVORITES(R.string.favorites, Icons.Outlined.Favorite, "home/favorites"),
    SETTINGS(R.string.settings, Icons.Outlined.Settings, "home/settings")
}

fun slideInHorizontallyWithFade(duration: Int) = slideInHorizontally(
    initialOffsetX = { -1000 }, animationSpec = tween(
        durationMillis = duration,
        easing = FastOutSlowInEasing
    )
) + fadeIn(animationSpec = tween(duration))

fun slideOutHorizontallyWithFade(duration: Int) = slideOutHorizontally(
    targetOffsetX = { -1000 }, animationSpec = tween(
        durationMillis = duration,
        easing = FastOutSlowInEasing
    )
) + fadeOut(animationSpec = tween(duration))

const val SHORT_DURATION = 700