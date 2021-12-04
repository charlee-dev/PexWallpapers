package com.adwi.feature_settings.presentation.about

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PrivacyTip
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.ExperimentalPagingApi
import com.adwi.components.Header
import com.adwi.components.PexScaffold
import com.adwi.components.theme.paddingValues
import com.adwi.feature_settings.domain.privacy.privacyCategoryList
import com.adwi.feature_settings.presentation.SettingsViewModel
import com.adwi.feature_settings.presentation.components.PrivacyCategoryPanel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@Composable
fun PrivacyPolicyScreen(
    viewModel: SettingsViewModel
) {
    PexScaffold(viewModel = viewModel) {
        Column(Modifier.fillMaxSize()) {
            Header(
                modifier = Modifier,
                title = "Privacy policy",
                icon = Icons.Outlined.PrivacyTip
            )
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxWidth()
            ) {
                items(items = privacyCategoryList) { category ->
                    PrivacyCategoryPanel(
                        name = category.name,
                        privacyItems = category.items
                    )
                }
            }
        }
    }
}