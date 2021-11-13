package com.adwi.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@ExperimentalComposeUiApi
@Composable
fun PexSearchToolbar(
    modifier: Modifier = Modifier,
    query: String,
    onHeroNameChanged: (String) -> Unit,
    onExecuteSearch: () -> Unit,
    onShowFilterDialog: () -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Row(
        modifier = modifier
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(.9f)
                .padding(8.dp),
            value = query,
            onValueChange = {
                onHeroNameChanged(it)
                onExecuteSearch()
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
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search Icon") },
            textStyle = TextStyle(color = MaterialTheme.colors.onBackground),
            colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background),
        )
        Column(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .clickable {
                    onShowFilterDialog()
                }
        ) {
            Icon(
                modifier = Modifier
                    .padding(8.dp),
                imageVector = Icons.Filled.MoreVert,
                contentDescription = "Filter Icon"
            )
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
            onHeroNameChanged = { text.value = it },
            onExecuteSearch = { },
            onShowFilterDialog = {}
        )
    }
}