package com.adwi.preview

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.annotation.ExperimentalCoilApi
import com.adwi.components.Header
import com.adwi.components.PexButton
import com.adwi.components.PexCoilImage
import com.adwi.components.theme.paddingValues
import com.adwi.composables.R
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun PreviewScreen(
    viewModel: PreviewViewModel = viewModel(),
    onSetWallpaperClick: (Int) -> Unit,
    upPress: () -> Unit
) {
    val preview by viewModel.preview.collectAsState()

    Image(
        painter = painterResource(id = R.drawable.pex_background),
        contentDescription = "",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.FillBounds
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Header(modifier = Modifier)
        PreviewCard(
            imageUrl = preview.imageUrlPortrait,
            modifier = Modifier
                .padding(paddingValues)
                .weight(1f)
        )
        PexButton(
            onClick = { onSetWallpaperClick(preview.id) },
            text = stringResource(id = R.string.set_wallpaper),
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
        )
    }
}

@ExperimentalMaterialApi
@ExperimentalCoilApi
@Composable
fun PreviewCard(
    modifier: Modifier = Modifier,
    placeholder: Int = R.drawable.daily_picture,
    imageUrl: String,
    elevation: Dp = 10.dp,
    shape: Shape = MaterialTheme.shapes.large,
) {
    Card(
        elevation = elevation,
        shape = shape,
        modifier = modifier
            .fillMaxSize()
    ) {
        PexCoilImage(
            imageUrl = imageUrl,
            placeholder = placeholder
        )
    }
}