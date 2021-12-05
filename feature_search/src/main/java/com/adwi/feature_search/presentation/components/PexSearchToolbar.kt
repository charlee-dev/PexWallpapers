package com.adwi.feature_search.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
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
import com.adwi.components.neumorphicShadow
import com.adwi.components.theme.PexWallpapersTheme
import com.adwi.components.theme.paddingValues
import com.adwi.feature_search.R

@ExperimentalComposeUiApi
@Composable
fun PexSearchToolbar(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChanged: (String) -> Unit,
    onShowFilterDialog: () -> Unit,
    shape: Shape = MaterialTheme.shapes.large,
    backgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = MaterialTheme.colors.primary
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Card(
        shape = shape,
        backgroundColor = backgroundColor,
        modifier = modifier
            .padding(paddingValues)
            .height(70.dp)
            .neumorphicShadow()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = paddingValues / 2)
        ) {
            TextField(
                modifier = Modifier
                    .weight(1f),
                leadingIcon = {
                    Icon(
                        Icons.Filled.Search,
                        contentDescription = stringResource(id = R.string.search),
                        tint = contentColor
                    )
                },
                value = query,
                onValueChange = { onQueryChanged(it) },
                label = { Text(text = stringResource(id = R.string.search)) },
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
                textStyle = TextStyle(color = contentColor),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                maxLines = 1,
                singleLine = true
            )
            IconButton(
                onClick = onShowFilterDialog
            ) {
                Icon(
                    modifier = Modifier,
                    imageVector = Icons.Filled.MoreVert,
                    tint = contentColor,
                    contentDescription = stringResource(id = R.string.filter)
                )
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
                onShowFilterDialog = {}
            )
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
                onShowFilterDialog = {}
            )
        }
    }
}