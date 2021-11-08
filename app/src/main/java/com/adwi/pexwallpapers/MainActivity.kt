package com.adwi.pexwallpapers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.adwi.camposables.WallpaperList
import com.adwi.core.domain.DataState
import com.adwi.core.domain.UIComponent
import com.adwi.core.domain.WallpaperListState
import com.adwi.core.util.Logger
import com.adwi.pexwallpapers.ui.theme.PexWallpapersTheme
import com.adwi.usecases.wallpaper.WallpaperUseCases
import com.squareup.sqldelight.android.AndroidSqliteDriver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val state: MutableState<WallpaperListState> = mutableStateOf(WallpaperListState())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val getCurated = WallpaperUseCases.build(
            sqlDriver = AndroidSqliteDriver(
                schema = WallpaperUseCases.schema,
                context = this,
                name = WallpaperUseCases.dbName,
            )
        ).getCuratedWallpapers

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
                    state.value = state.value.copy(wallpapers = dataState.data ?: listOf())
                }
                is DataState.Loading -> {
                    state.value = state.value.copy(progressBarState = dataState.progressBarState)
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
                    WallpaperList(
                        state = state.value
                    )
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