package com.adwi.pexwallpapers.ui.main

import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import coil.annotation.ExperimentalCoilApi
import com.adwi.base.BaseActivity
import com.adwi.components.theme.PexWallpapersTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoilApi
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@InternalCoroutinesApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : BaseActivity() {

    override val viewModel: MainViewModel by viewModels()

    override fun init() {}

    override fun setContent() {
        setContent {
            PexWallpapersTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    WallpaperList(
//                        state = state.value
//                    )
                }
            }
        }
    }
}