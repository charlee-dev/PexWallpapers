package com.adwi.feature_search.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.adwi.components.theme.paddingValues

@Composable
fun NothingHereYetMessage(
    visible: Boolean
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Nothing here yet",
                    color = MaterialTheme.colors.onBackground,
                    style = MaterialTheme.typography.h5
                )
                Text(
                    text = "Press \"Search bar\" to start new search",
                    color = MaterialTheme.colors.onBackground,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(paddingValues / 2)
                )
                Text(
                    text = "Shall we?",
                    color = MaterialTheme.colors.onBackground,
                    style = MaterialTheme.typography.h5
                )
            }
        }
    }
}