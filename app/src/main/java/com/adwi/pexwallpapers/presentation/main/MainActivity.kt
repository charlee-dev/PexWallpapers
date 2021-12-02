package com.adwi.pexwallpapers.presentation.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import coil.annotation.ExperimentalCoilApi
import com.adwi.components.theme.PexWallpapersTheme
import com.adwi.pexwallpapers.domain.work.cancelAutoChangeWorks
import com.adwi.pexwallpapers.presentation.screens.PexApp
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect

@ExperimentalPermissionsApi
@ExperimentalCoilApi
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@InternalCoroutinesApi
@ExperimentalMaterialApi
@ExperimentalPagingApi
@ExperimentalPagerApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getToasts()

        setContent {
            ProvideWindowInsets {
                PexWallpapersTheme {
                    PexApp(
                        viewModel = viewModel,
                        onSaveAutomationClick = { viewModel.saveAutomation(this) },
                        cancelWorks = { this.cancelAutoChangeWorks() },
                        onShareClick = { viewModel.shareWallpaper(this, it) },
                        onDownloadClick = { viewModel.downloadWallpaper(this, it) },
                        onSetWallpaperClick = { url, home, lock ->
                            viewModel.setWallpaper(
                                context = this,
                                imageUrl = url,
                                setHomeScreen = home,
                                setLockScreen = lock
                            )
                        }
                    )
                }
            }
        }
    }

    private fun getToasts() {
        run {
            lifecycleScope.launchWhenStarted {
                viewModel.toastMessage.collect { message ->
                    Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}