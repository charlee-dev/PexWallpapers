package com.adwi.components

import androidx.compose.animation.*
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adwi.components.theme.AccentMedium
import com.adwi.components.theme.PexWallpapersTheme
import com.adwi.components.theme.paddingValues
import com.adwi.core.Resource

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun PexButton(
    modifier: Modifier = Modifier,
    state: Resource = Resource.Idle,
    enabled: Boolean = true,
    text: String = "Set wallpaper",
    loadingText: String = "Loading",
    successText: String = "Success",
    errorText: String = "Error",
    shape: Shape = MaterialTheme.shapes.large,
    backgroundColor: Color = MaterialTheme.colors.primaryVariant,
    textColor: Color = MaterialTheme.colors.primary,
    onClick: () -> Unit = {}
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val transition = updateTransition(targetState = state, label = "Button state")

    val bgColor by transition.animateColor(
        label = "Card background color"
    ) { result ->
        when (result) {
            is Resource.Error -> Color.Red
            Resource.Idle -> backgroundColor
            is Resource.Loading -> textColor
            is Resource.Success -> AccentMedium
        }
    }

    val titleColor by transition.animateColor(
        label = "Card background color"
    ) { result ->
        when (result) {
            is Resource.Error -> Color.White
            Resource.Idle -> textColor
            is Resource.Loading -> backgroundColor
            is Resource.Success -> Color.White
        }
    }

    Surface(
        onClick = onClick,
        enabled = state is Resource.Idle && enabled,
        shape = shape,
        modifier = modifier.neumorphicShadow(
            offset = 0.dp,
            pressed = isPressed
        ),
        color = bgColor,
    ) {
        AnimatedContent(
            targetState = state,
            transitionSpec = {
                if (targetState == Resource.Idle) {
                    slideInVertically(initialOffsetY = { height -> height }) + fadeIn() with
                            slideOutVertically(targetOffsetY = { height -> -height }) + fadeOut()
                } else {
                    slideInVertically(initialOffsetY = { height -> -height }) + fadeIn() with
                            slideOutVertically(targetOffsetY = { height -> height }) + fadeOut()
                }.using(SizeTransform(clip = false))
            },
            modifier = Modifier.fillMaxSize()
        ) { targetState ->
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = when (targetState) {
                        is Resource.Error -> errorText
                        Resource.Idle -> text
                        is Resource.Loading -> loadingText
                        is Resource.Success -> successText
                    }.uppercase(),
                    color = titleColor,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Preview(showBackground = true, name = "Light")
@Composable
private fun PexButtonPreviewLight() {
    PexWallpapersTheme(darkTheme = false) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(paddingValues)
        ) {
            PexButton(
                text = "Save",
                modifier = Modifier.height(42.dp),
                state = Resource.Idle,
                loadingText = "Loading",
                errorText = "Error",
                successText = "Success"
            )
            Spacer(modifier = Modifier.size(paddingValues))
            PexButton(
                text = "Save",
                modifier = Modifier.height(42.dp),
                state = Resource.Loading(),
                loadingText = "Loading",
                errorText = "Error",
                successText = "Success"
            )
            Spacer(modifier = Modifier.size(paddingValues))
            PexButton(
                text = "Save",
                modifier = Modifier.height(42.dp),
                state = Resource.Success(),
                loadingText = "Loading",
                errorText = "Error",
                successText = "Success"
            )
            Spacer(modifier = Modifier.size(paddingValues))
            PexButton(
                text = "Save",
                modifier = Modifier.height(42.dp),
                state = Resource.Error("Unknown error"),
                loadingText = "Loading",
                errorText = "Error",
                successText = "Success"
            )
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Preview(showBackground = true, name = "Dark")
@Composable
private fun PexButtonPreviewDark() {
    PexWallpapersTheme(darkTheme = true) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(paddingValues)
        ) {
            PexButton(
                text = "Save",
                modifier = Modifier.height(42.dp),
                state = Resource.Idle,
                loadingText = "Loading",
                errorText = "Error",
                successText = "Success"
            )
            Spacer(modifier = Modifier.size(paddingValues))
            PexButton(
                text = "Save",
                modifier = Modifier.height(42.dp),
                state = Resource.Loading(),
                loadingText = "Loading",
                errorText = "Error",
                successText = "Success"
            )
            Spacer(modifier = Modifier.size(paddingValues))
            PexButton(
                text = "Save",
                modifier = Modifier.height(42.dp),
                state = Resource.Success(),
                loadingText = "Loading",
                errorText = "Error",
                successText = "Success"
            )
            Spacer(modifier = Modifier.size(paddingValues))
            PexButton(
                text = "Save",
                modifier = Modifier.height(42.dp),
                state = Resource.Error("Unknown error"),
                loadingText = "Loading",
                errorText = "Error",
                successText = "Success"
            )
        }
    }
}