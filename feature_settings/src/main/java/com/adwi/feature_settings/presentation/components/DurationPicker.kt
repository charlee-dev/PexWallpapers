package com.adwi.feature_settings.presentation.components

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircleOutline
import androidx.compose.material.icons.outlined.RemoveCircleOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adwi.components.neumorphicShadow
import com.adwi.components.theme.PexWallpapersTheme
import com.adwi.components.theme.paddingValues
import com.adwi.feature_settings.R

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun DurationPicker(
    modifier: Modifier = Modifier,
    days: Int,
    hours: Int,
    minutes: Int,
    onDaysChange: (Int) -> Unit,
    onHourChange: (Int) -> Unit,
    onMinutesChange: (Int) -> Unit,
) {
    val dayMax = 31
    val hoursMax = 23
    val minutesMax = 59

    Column(
        modifier
            .fillMaxWidth()
            .padding(horizontal = paddingValues)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Days",
                modifier = Modifier.align(Alignment.CenterStart),
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.onBackground
            )
            TimeUnitItem(
                count = days,
                range = dayMax,
                onUpClick = { onDaysChange(days + 1) },
                onDownClick = { onDaysChange(days - 1) },
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Hours",
                modifier = Modifier.align(Alignment.CenterStart),
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.onBackground
            )
            TimeUnitItem(
                count = hours,
                range = hoursMax,
                onUpClick = { onHourChange(hours + 1) },
                onDownClick = { onHourChange(hours - 1) },
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Minutes",
                modifier = Modifier.align(Alignment.CenterStart),
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.onBackground
            )
            TimeUnitItem(
                count = minutes,
                range = minutesMax,
                onUpClick = { onMinutesChange(minutes + 15) },
                onDownClick = { onMinutesChange(minutes - 15) },
                modifier = Modifier.align(Alignment.Center)

            )
        }
    }
}

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
private fun TimeUnitItem(
    modifier: Modifier = Modifier,
    count: Int,
    range: Int,
    onUpClick: () -> Unit,
    onDownClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
    ) {

        IconButton(
            onClick = onDownClick,
            enabled = count > 0
        ) {
            Icon(
                imageVector = Icons.Outlined.RemoveCircleOutline,
                contentDescription = stringResource(R.string.down),
                tint = MaterialTheme.colors.onBackground
            )
        }
        AnimatedCounter(count = count)
        IconButton(
            onClick = onUpClick,
            enabled = count < range
        ) {
            Icon(
                imageVector = Icons.Outlined.AddCircleOutline,
                contentDescription = stringResource(R.string.up),
                tint = MaterialTheme.colors.onBackground
            )
        }
    }
}

@ExperimentalAnimationApi
@Composable
private fun AnimatedCounter(
    modifier: Modifier = Modifier,
    count: Int
) {
    Surface(
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colors.primary
        ),
        shape = RoundedCornerShape(percent = 20),
        modifier = modifier
            .size(40.dp)
            .neumorphicShadow(
                cornerRadius = 10.dp,
                offset = (-5).dp
            ),
        color = MaterialTheme.colors.primary,
//        elevation = 4.dp
    ) {
        Row(
            Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnimatedContent(
                targetState = count,
                transitionSpec = {
                    if (targetState > initialState) {
                        slideInVertically(initialOffsetY = { height -> -height }) + fadeIn() with
                                slideOutVertically(targetOffsetY = { height -> height }) + fadeOut()
                    } else {
                        slideInVertically(initialOffsetY = { height -> height }) + fadeIn() with
                                slideOutVertically(targetOffsetY = { height -> -height }) + fadeOut()
                    }.using(
                        SizeTransform(clip = false)
                    )
                }
            ) { targetCount ->
                Text(
                    text = "$targetCount",
                    modifier = Modifier.padding(4.dp),
                    color = MaterialTheme.colors.primaryVariant
                )
            }
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Preview(showBackground = true, name = "Light")
@Composable
private fun DurationPickerPreviewLight() {
    PexWallpapersTheme(darkTheme = false) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(paddingValues)
        ) {
            DurationPicker(
                days = 0,
                hours = 5,
                minutes = 21,
                onDaysChange = {},
                onHourChange = {},
                onMinutesChange = {}
            )
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Preview(showBackground = true, name = "Dark")
@Composable
private fun DurationPickerPreviewDark() {
    PexWallpapersTheme(darkTheme = true) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(paddingValues)
        ) {
            DurationPicker(
                days = 0,
                hours = 5,
                minutes = 21,
                onDaysChange = {},
                onHourChange = {},
                onMinutesChange = {}
            )
        }
    }
}