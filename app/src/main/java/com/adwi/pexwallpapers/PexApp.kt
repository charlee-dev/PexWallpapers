package com.adwi.pexwallpapers

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.ExperimentalPagingApi
import coil.annotation.ExperimentalCoilApi
import com.adwi.components.PexSnackBar
import com.adwi.components.theme.PexWallpapersTheme
import com.adwi.core.base.BaseViewModel
import com.adwi.pexwallpapers.ui.components.PexBottomBar
import com.adwi.pexwallpapers.ui.components.PexScaffold
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.systemBarsPadding
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi


@ExperimentalCoroutinesApi
@ExperimentalComposeUiApi
@InternalCoroutinesApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalCoilApi
@ExperimentalPagingApi
@ExperimentalFoundationApi
@Composable
fun PexApp(
    viewModel: BaseViewModel
) {
    ProvideWindowInsets {
        PexWallpapersTheme() {
            val appState = rememberMyAppState()

            PexScaffold(
                viewModel = viewModel,
                bottomBar = {
                    if (appState.shouldShowBottomBar) {
                        PexBottomBar(
                            tabs = appState.bottomBarTabs,
                            currentRoute = appState.currentRoute!!,
                            navigateToRoute = appState::navigateToBottomBarRoute
                        )
                    }
                },
                snackbarHost = {
                    SnackbarHost(
                        hostState = it,
                        modifier = Modifier
                            .systemBarsPadding()
                            // Limit the Snackbar width for large screens
                            .wrapContentWidth(align = Alignment.Start)
                            .widthIn(max = 550.dp),
                        snackbar = { snackbarData -> PexSnackBar(snackbarData) }
                    )
                }
            ) {
                AnimatedNavHost(
                    navController = appState.navController,
                    startDestination = MainDestinations.HOME_ROUTE,
                    modifier = Modifier
                ) {
                    myNavGraph(
                        upPress = appState::upPress,
                        onWallpaperClick = appState::navigateToPreview,
                        onCategoryClick = {
                            appState.navigateToBottomBarRoute(HomeSections.SEARCH.route)
                        },
                        onSetWallpaperClick = appState::navigateToSetWallpaper
                    )
                }
            }
        }
    }
}