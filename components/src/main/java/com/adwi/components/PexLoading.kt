package com.adwi.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.adwi.composables.R

@Composable
fun PexLoading(
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.loading),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}