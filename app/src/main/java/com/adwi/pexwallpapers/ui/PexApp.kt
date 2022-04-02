package com.adwi.pexwallpapers.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.paging.ExperimentalPagingApi
import coil.annotation.ExperimentalCoilApi
import com.adwi.components.PexScaffold
import com.adwi.components.PexSnackBarHost
import com.adwi.components.theme.PexWallpapersTheme
import com.adwi.core.Event
import com.adwi.pexwallpapers.R
import com.adwi.pexwallpapers.ui.components.PexBottomBar
import com.adwi.pexwallpapers.ui.main.MainViewModel
import com.adwi.pexwallpapers.ui.navigation.HomeSections
import com.adwi.pexwallpapers.ui.navigation.MainDestinations
import com.adwi.pexwallpapers.ui.navigation.myNavGraph
import com.adwi.pexwallpapers.ui.navigation.rememberMyAppState
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalPermissionsApi
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
    viewModel: MainViewModel
) {
    ProvideWindowInsets {
        PexWallpapersTheme {

            val appState = rememberMyAppState()
            val context = LocalContext.current

            PexScaffold(
                viewModel = viewModel,
                bottomBar = {
                    if (appState.shouldShowBottomBar) {
                        PexBottomBar(
                            tabs = appState.bottomBarTabs,
                            currentRoute = appState.currentRoute!!,
                            navigateToRoute = appState::navigateToBottomBarRoute,
                            showShadows = viewModel.showShadows
                        )
                    }
                },
                snackbarHost = {
                    PexSnackBarHost(
                        hostState = it,
                        modifier = Modifier
                    )
                },
                scaffoldState = appState.scaffoldState
            ) {
                AnimatedNavHost(
                    navController = appState.navController,
                    startDestination = MainDestinations.HOME_ROUTE,
                    modifier = Modifier
                ) {
                    myNavGraph(
                        settings = viewModel.settings.value,
                        upPress = appState::upPress,
                        onWallpaperClick = appState::navigateToPreview,
                        onCategoryClick = appState::navigateToCategory,
                        navigateToSearch = { appState.navigateToBottomBarRoute(HomeSections.SEARCH.route) },
                        onAboutUsClick = { viewModel.aboutUs() },
                        onPrivacyPolicyClick = appState::navigateToPrivacyPolicy,
                        onContactSupportClick = { viewModel.contactSupport() },
                        onSaveAutomationClick = { viewModel.saveAutomation() },
                        cancelWorks = { viewModel.cancelAutoChangeWorks() },
                        onShareClick = { viewModel.shareWallpaper(context, it) },
                        onDownloadClick = { viewModel.downloadWallpaper(it) },
                        onGiveFeedbackClick = {
//                            TODO("implement give feedback")
                            viewModel.setSnackBar("Coming soon")
                        },
                        onRequestFeature = {
//                            TODO("implement request feature")
                            viewModel.setSnackBar("Coming soon")
                        },
                        onReportBugClick = {
//                            TODO("implement report bug")
                            viewModel.setSnackBar("Coming soon")
                        },
                        onHomeClick = { url ->
                            viewModel.setWallpaper(
                                imageUrl = url,
                                setHomeScreen = true,
                                setLockScreen = false
                            )
                            viewModel.setEvent(
                                Event.ShowToast(
                                    context.getString(R.string.home_wallpaper_set)
                                )
                            )
                        },
                        onLockClick = { url ->
                            viewModel.setWallpaper(
                                imageUrl = url,
                                setHomeScreen = false,
                                setLockScreen = true
                            )
                            viewModel.setEvent(
                                Event.ShowToast(
                                    context.getString(R.string.lock_wallpaper_set)
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}