package com.adwi.pexwallpapers.ui.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import com.adwi.pexwallpapers.domain.state.Event
import com.adwi.pexwallpapers.ui.base.BaseViewModel
import com.adwi.pexwallpapers.ui.theme.Neutral3

@Composable
fun PexScaffold(
    modifier: Modifier = Modifier,
    viewModel: BaseViewModel,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    topBar: @Composable (() -> Unit) = {},
    bottomBar: @Composable (() -> Unit) = {},
    snackbarHost: @Composable (SnackbarHostState) -> Unit = { PexSnackBarHost(hostState = it) },
    floatingActionButton: @Composable (() -> Unit) = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    isFloatingActionButtonDocked: Boolean = false,
    drawerContent: @Composable (ColumnScope.() -> Unit)? = null,
    drawerShape: Shape = MaterialTheme.shapes.large,
    drawerElevation: Dp = DrawerDefaults.Elevation,
    drawerBackgroundColor: Color = MaterialTheme.colors.background,
    drawerContentColor: Color = MaterialTheme.colors.onBackground,
    drawerScrimColor: Color = Neutral3,
    backgroundColor: Color = MaterialTheme.colors.background,
    contentColor: Color = MaterialTheme.colors.onBackground,
    content: @Composable (PaddingValues) -> Unit
) {
    val snackbarMessage by viewModel.snackBarMessage.collectAsState("")

    if (snackbarMessage.isNotEmpty()) {
        LaunchedEffect(scaffoldState.snackbarHostState) {
            scaffoldState.snackbarHostState.showSnackbar(snackbarMessage)
            viewModel.setEvent(Event.ShowSnackBar(""))
        }
    }

    Scaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        topBar = topBar,
        bottomBar = bottomBar,
        snackbarHost = snackbarHost,
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = floatingActionButtonPosition,
        isFloatingActionButtonDocked = isFloatingActionButtonDocked,
        drawerContent = drawerContent,
        drawerShape = drawerShape,
        drawerElevation = drawerElevation,
        drawerBackgroundColor = drawerBackgroundColor,
        drawerContentColor = drawerContentColor,
        drawerScrimColor = drawerScrimColor,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        content = content
    )
}