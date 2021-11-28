package com.adwi.pexwallpapers.presentation.components

import androidx.compose.animation.*
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adwi.pexwallpapers.R
import com.adwi.pexwallpapers.domain.state.Result
import com.adwi.pexwallpapers.presentation.theme.AccentMedium

@Composable
fun ButtonRoundedTop(
    modifier: Modifier = Modifier,
    onSetWallpaperClick: () -> Unit,
    text: String,
    backgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = MaterialTheme.colors.onSurface
) {
    Button(
        onClick = onSetWallpaperClick,
        shape = MaterialTheme.shapes.medium,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
            contentColor = contentColor
        )

    ) {
        Text(text = text)
    }
}


@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun PexButton(
    modifier: Modifier = Modifier,
    state: Result = Result.Idle,
    text: String,
    loadingText: String = stringResource(id = R.string.loading),
    successText: String = stringResource(id = R.string.done),
    errorText: String = stringResource(id = R.string.error),
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
            is Result.Error -> Color.Red
            Result.Idle -> backgroundColor
            Result.Loading -> textColor
            Result.Success -> AccentMedium
        }
    }

    val titleColor by transition.animateColor(
        label = "Card background color"
    ) { result ->
        when (result) {
            is Result.Error -> Color.White
            Result.Idle -> textColor
            Result.Loading -> backgroundColor
            Result.Success -> Color.White
        }
    }

    Surface(
        onClick = onClick,
        enabled = state is Result.Idle,
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
                if (targetState == Result.Idle) {
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
                        is Result.Error -> errorText
                        Result.Idle -> text
                        Result.Loading -> loadingText
                        Result.Success -> successText
                    }.uppercase(),
                    color = titleColor,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
@Preview(showBackground = true)
private fun PexButtonPreview() {
    Column {
        PexButton(
            text = "Save"
        )
    }
}