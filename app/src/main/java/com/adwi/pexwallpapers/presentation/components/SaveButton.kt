package com.adwi.pexwallpapers.presentation.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.adwi.pexwallpapers.R
import com.adwi.pexwallpapers.domain.state.Resource
import com.adwi.pexwallpapers.presentation.theme.paddingValues
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

@ExperimentalAnimationApi
@ExperimentalPermissionsApi
@ExperimentalMaterialApi
@Composable
fun SaveButton(
    modifier: Modifier = Modifier,
    state: Resource = Resource.Idle,
    enabled: Boolean,
    onClick: () -> Unit
) {
    var doNotShowRationale by rememberSaveable {
        mutableStateOf(false)
    }

    val readPermissionState = rememberPermissionState(
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    )

    when {
        readPermissionState.hasPermission -> {
            PexButton(
                state = state,
                enabled = enabled,
                onClick = onClick,
                shape = RectangleShape,
                text = stringResource(id = R.string.save),
                successText = stringResource(id = R.string.automation_saved),
                modifier = modifier
            )

        }
        readPermissionState.shouldShowRationale ||
                !readPermissionState.permissionRequested -> {
            if (doNotShowRationale) {
                Text(
                    text = stringResource(R.string.feature_not_available),
                    modifier = Modifier.padding(paddingValues)
                )
            } else {
                Column(
                    modifier = Modifier
                        .padding(horizontal = paddingValues)
                        .padding(bottom = paddingValues)
                ) {
                    Text(stringResource(R.string.external_storage_rationale))
                    Spacer(modifier = Modifier.height(8.dp))
                    PexButton(
                        onClick = { readPermissionState.launchPermissionRequest() },
                        text = stringResource(R.string.request_permission),
                        backgroundColor = Color.Green,
                        textColor = Color.Black,
                        modifier = Modifier.height(40.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    PexButton(
                        onClick = { doNotShowRationale = true },
                        text = stringResource(R.string.dont_show_rationale_again),
                        backgroundColor = Color.Red,
                        textColor = Color.White,
                        modifier = Modifier.height(40.dp)
                    )
                }
            }
        }
        else -> {
            Text(
                text = stringResource(R.string.external_storage_denied),
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}