package com.adwi.pexwallpapers

import androidx.annotation.StringRes
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.paging.ExperimentalPagingApi
import coil.annotation.ExperimentalCoilApi
import com.adwi.favorites.FavoritesEvent
import com.adwi.favorites.FavoritesScreen
import com.adwi.favorites.FavoritesViewModel
import com.adwi.home.HomeScreen
import com.adwi.home.HomeViewModel
import com.adwi.preview.PreviewScreen
import com.adwi.preview.PreviewViewModel
import com.adwi.search.SearchEvents
import com.adwi.search.SearchScreen
import com.adwi.search.SearchViewModel
import com.adwi.settings.SettingsScreen
import com.google.accompanist.navigation.animation.composable
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
@ExperimentalPagingApi
@ExperimentalFoundationApi
fun NavGraphBuilder.myNavGraph(
    onWallpaperClick: (Int, NavBackStackEntry) -> Unit,
    onCategoryClick: () -> Unit,
    onSetWallpaperClick: (Int, NavBackStackEntry) -> Unit,
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
        val viewModel = hiltViewModel<PreviewViewModel>(backStackEntry)

        PreviewScreen(
            state = viewModel.state.value,
            onSetWallpaperClick = { id -> onSetWallpaperClick(id, backStackEntry) },
            upPress = upPress
        )
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
@ExperimentalPagingApi
@ExperimentalFoundationApi
@ExperimentalComposeUiApi
fun NavGraphBuilder.addHomeGraph(
    onWallpaperClick: (Int, NavBackStackEntry) -> Unit,
    onCategoryClick: () -> Unit
) {
    composable(
        HomeSections.HOME.route,
        enterTransition = { initial, _ ->
            when (initial.destination.route) {
                HomeSections.SEARCH.route -> slideInHorizontallyWithFade(SHORT_DURATION)
                HomeSections.FAVORITES.route -> slideInHorizontallyWithFade(SHORT_DURATION)
                HomeSections.SETTINGS.route -> slideInHorizontallyWithFade(SHORT_DURATION)
                else -> null
            }
        },
        exitTransition = { _, target ->
            when (target.destination.route) {
                HomeSections.SEARCH.route -> slideOutHorizontallyWithFade(SHORT_DURATION)
                HomeSections.FAVORITES.route -> slideOutHorizontallyWithFade(SHORT_DURATION)
                HomeSections.SETTINGS.route -> slideOutHorizontallyWithFade(SHORT_DURATION)
                else -> null
            }
        }
    ) { backStackEntry ->
        val viewModel = hiltViewModel<HomeViewModel>(backStackEntry)
        HomeScreen(
            state = viewModel.state.value,
            onTriggerEvent = viewModel::onTriggerEvent,
            onWallpaperClick = { id -> onWallpaperClick(id, backStackEntry) },
            onCategoryClick = onCategoryClick
        )
    }
    composable(
        route = HomeSections.SEARCH.route,
        enterTransition = { initial, _ ->
            when (initial.destination.route) {
                HomeSections.FAVORITES.route -> slideInHorizontallyWithFade(SHORT_DURATION)
                HomeSections.SETTINGS.route -> slideInHorizontallyWithFade(SHORT_DURATION)
                else -> null
            }
        },
        exitTransition = { _, target ->
            when (target.destination.route) {
                HomeSections.FAVORITES.route -> slideOutHorizontallyWithFade(SHORT_DURATION)
                HomeSections.SETTINGS.route -> slideOutHorizontallyWithFade(SHORT_DURATION)
                else -> null
            }
        }
    ) { backStackEntry ->
        val viewModel = hiltViewModel<SearchViewModel>(backStackEntry)

        viewModel.onTriggerEvent(SearchEvents.RestoreLastQuery)

        SearchScreen(
            viewModel = viewModel,
            onWallpaperClick = { id -> onWallpaperClick(id, backStackEntry) }
        )
    }
    composable(
        route = HomeSections.FAVORITES.route,
        enterTransition = { initial, _ ->
            when (initial.destination.route) {
                HomeSections.SETTINGS.route -> slideInHorizontallyWithFade(SHORT_DURATION)
                else -> null
            }
        },
        exitTransition = { _, target ->
            when (target.destination.route) {
                HomeSections.SETTINGS.route -> slideOutHorizontallyWithFade(SHORT_DURATION)
                else -> null
            }
        }
    ) { backStackEntry ->
        val viewModel = hiltViewModel<FavoritesViewModel>(backStackEntry)

        viewModel.onTriggerEvent(FavoritesEvent.GetFavorites)

        FavoritesScreen(
            state = viewModel.state.value,
            onTriggerEvent = viewModel::onTriggerEvent,
            onSearchClick = onCategoryClick, // onCategoryClick navigates to Search only
            onWallpaperClick = { id -> onWallpaperClick(id, backStackEntry) }
        )
    }
    composable(HomeSections.SETTINGS.route) { backStackEntry ->
//        val viewModel = hiltViewModel<SettingsViewModel>(backStackEntry)
        SettingsScreen(
//            viewModel = viewModel
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
    FAVORITES(R.string.favorites, Icons.Outlined.Bookmark, "home/bookmark"),
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

const val SHORT_DURATION = 300