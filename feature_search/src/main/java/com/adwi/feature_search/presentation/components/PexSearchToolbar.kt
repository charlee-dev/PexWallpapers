package com.adwi.feature_search.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adwi.components.ShowMenuButton
import com.adwi.components.neumorphicShadow
import com.adwi.components.theme.Dimensions
import com.adwi.components.theme.PexWallpapersTheme
import com.adwi.components.theme.paddingValues
import com.adwi.feature_search.R

@ExperimentalComposeUiApi
@Composable
fun PexSearchToolbar(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChanged: (String) -> Unit,
    shape: Shape = MaterialTheme.shapes.large,
    backgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = MaterialTheme.colors.primary,
    showShadows: Boolean,
    menuItems: @Composable () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    var expanded by remember { mutableStateOf(false) }
    val transition = updateTransition(targetState = expanded, label = "Header")

    val backgroundColorState by transition.animateColor(label = "Color") { state ->
        if (state) MaterialTheme.colors.primary else backgroundColor
    }
    val contentColorState by transition.animateColor(label = "Color") { state ->
        if (state) MaterialTheme.colors.secondary else contentColor
    }
    val buttonColorState by transition.animateColor(label = "Color") { state ->
        if (state) MaterialTheme.colors.primaryVariant else contentColor
    }

    Card(
        shape = shape,
        backgroundColor = backgroundColor,
        modifier = modifier
            .padding(paddingValues)
            .neumorphicShadow(enabled = showShadows, offset = (-5).dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.animateContentSize()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimensions.Button)
                    .background(backgroundColorState)
                    .padding(start = paddingValues / 2)
            ) {
                TextField(
                    modifier = Modifier
                        .weight(1f),
                    leadingIcon = {
                        Icon(
                            Icons.Filled.Search,
                            contentDescription = stringResource(id = R.string.search),
                            tint = contentColorState
                        )
                    },
                    value = query,
                    onValueChange = { onQueryChanged(it) },
                    label = {
                        Text(
                            text = stringResource(id = R.string.search),
                            color = contentColorState,
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done,
                        autoCorrect = true
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                        }
                    ),
                    textStyle = TextStyle(color = contentColorState),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = buttonColorState
                    ),
                    maxLines = 1,
                    singleLine = true
                )
                ShowMenuButton(
                    onClick = { expanded = !expanded },
                    expanded = expanded,
                    buttonColor = buttonColorState
                )
            }
            AnimatedVisibility(expanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.surface),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Column(
                            modifier = Modifier
                                .padding(paddingValues / 2)
                                .align(Alignment.Center)
                        ) {
                            menuItems()
                        }
                    }
                }
            }
        }
    }
}

@ExperimentalComposeUiApi
@Preview(showBackground = true, name = "Light")
@Composable
private fun SearchToolbarPreviewLight() {
    PexWallpapersTheme(darkTheme = false) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(paddingValues)
        ) {
            PexSearchToolbar(
                query = "Pex Wallpapers",
                onQueryChanged = { },
                showShadows = true
            ) {}
        }
    }
}

@ExperimentalComposeUiApi
@Preview(showBackground = true, name = "Dark")
@Composable
private fun SearchToolbarPreviewDark() {
    PexWallpapersTheme(darkTheme = true) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(paddingValues)
        ) {
            PexSearchToolbar(
                query = "Pex Wallpapers",
                onQueryChanged = { },
                showShadows = true
            ) {}
        }
    }
}