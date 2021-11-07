package com.adwi.pexwallpapers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.adwi.core.domain.DataState
import com.adwi.core.domain.ProgressBarState
import com.adwi.core.domain.UIComponent
import com.adwi.core.util.Logger
import com.adwi.domain.Wallpaper
import com.adwi.pexwallpapers.ui.theme.PexWallpapersTheme
import com.adwi.usecases.wallpaper.WallpaperUseCases
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : ComponentActivity() {

    private val wallpapers: MutableState<List<Wallpaper>> = mutableStateOf(listOf())
    private val progressbarState: MutableState<ProgressBarState> =
        mutableStateOf(ProgressBarState.Idle)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val getCurated = WallpaperUseCases.build().getCuratedWallpapers
        val logger = Logger("GetCuratedWallpapers")

        getCurated.execute().onEach { dataState ->
            when (dataState) {
                is DataState.Response -> {
                    when (dataState.uiComponent) {
                        is UIComponent.Dialog -> {
                            logger.log((dataState.uiComponent as UIComponent.Dialog).description)
                        }
                        is UIComponent.None -> {
                            logger.log((dataState.uiComponent as UIComponent.None).message)
                        }
                    }
                }
                is DataState.Data -> {
                    wallpapers.value = dataState.data ?: listOf()
                }
                is DataState.Loading -> {
                    progressbarState.value = dataState.progressBarState
                }
            }
        }.launchIn(CoroutineScope(Dispatchers.IO))

        setContent {
            PexWallpapersTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        LazyColumn {
                            items(wallpapers.value) { wallpaper ->
                                Text(text = wallpaper.photographer)
                            }
                        }
                        if (progressbarState.value is ProgressBarState.Loading) {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PexWallpapersTheme {
        Greeting("Android")
    }
}