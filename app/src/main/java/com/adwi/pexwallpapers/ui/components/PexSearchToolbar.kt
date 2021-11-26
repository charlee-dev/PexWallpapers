package com.adwi.pexwallpapers.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.adwi.pexwallpapers.ui.theme.paddingValues

@ExperimentalComposeUiApi
@Composable
fun PexSearchToolbar(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChanged: (String) -> Unit,
    onExecuteSearch: () -> Unit,
    onShowFilterDialog: () -> Unit,
    elevation: Dp = 10.dp,
    shape: Shape = MaterialTheme.shapes.large,
    backgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = MaterialTheme.colors.primary
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Card(
        elevation = elevation,
        shape = shape,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = paddingValues, bottom = paddingValues / 2),
                value = query,
                onValueChange = {
                    onQueryChanged(it)
                },
                label = { Text(text = "Search") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        onExecuteSearch()
                        keyboardController?.hide()
                    },
                ),
                leadingIcon = {
                    Icon(
                        Icons.Filled.Search,
                        contentDescription = "Search Icon",
                        tint = contentColor
                    )
                },
                textStyle = TextStyle(color = contentColor),
                colors = TextFieldDefaults.textFieldColors(backgroundColor = backgroundColor),
                maxLines = 1
            )
            IconButton(
                onClick = onShowFilterDialog
            ) {
                Icon(
                    modifier = Modifier
//                        .padding(8.dp)
                    ,
                    imageVector = Icons.Filled.MoreVert,
                    tint = contentColor,
                    contentDescription = "Filter Icon"
                )
            }
        }
    }
}

@ExperimentalComposeUiApi
@Preview(showBackground = true)
@Composable
private fun SearchToolbarPreview() {
    MaterialTheme {
        val text = remember { mutableStateOf("PexWalls") }
        PexSearchToolbar(
            query = text.value,
            onQueryChanged = { text.value = it },
            onExecuteSearch = { },
            onShowFilterDialog = {}
        )
    }
}