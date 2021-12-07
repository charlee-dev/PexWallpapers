package com.adwi.feature_settings.presentation.about

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PrivacyTip
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.paging.ExperimentalPagingApi
import com.adwi.components.CategoryTitle
import com.adwi.components.Header
import com.adwi.components.PexExpandableCard
import com.adwi.components.PexScaffold
import com.adwi.components.theme.paddingValues
import com.adwi.feature_settings.domain.privacy.privacyCategoryList
import com.adwi.feature_settings.presentation.SettingsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@Composable
fun PrivacyPolicyScreen(
    viewModel: SettingsViewModel,
    upPress: () -> Unit
) {
    PexScaffold(viewModel = viewModel) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                Header(
                    hasUpPress = true,
                    onUpPress = upPress,
                    modifier = Modifier,
                    title = "Privacy policy",
                    icon = Icons.Outlined.PrivacyTip,
                    actionIcon = null,
                    showShadows = viewModel.showShadows
                )
            }
            items(items = privacyCategoryList) { category ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = paddingValues),
                ) {
                    CategoryTitle(
                        name = category.name,
                        modifier = Modifier.padding(bottom = paddingValues)
                    )
                    category.items.forEach { item ->
                        PexExpandableCard(
                            modifier = Modifier,
                            headerText = item.title,
                            showShadows = viewModel.showShadows
                        ) {
                            PrivacyItemDescription(
                                text = item.description,
                                modifier = Modifier.padding(bottom = paddingValues)
                            )
                        }
                        Spacer(modifier = Modifier.size(paddingValues))
                    }
                }
            }
        }
    }
}

@Composable
fun PrivacyItemDescription(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = MaterialTheme.colors.onBackground,
    style: TextStyle = MaterialTheme.typography.subtitle1
) {
    Text(
        text = text,
        color = color,
        style = style,
        modifier = modifier.padding(horizontal = paddingValues)
    )
}