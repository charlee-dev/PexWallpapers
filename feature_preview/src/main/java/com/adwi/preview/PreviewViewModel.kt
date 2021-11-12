package com.adwi.preview

import androidx.lifecycle.viewModelScope
import com.adwi.core.base.BaseViewModel
import com.adwi.datasource.local.domain.toDomain
import com.adwi.domain.Wallpaper
import com.adwi.interactors.wallpaper.WallpaperInteractors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class PreviewViewModel @Inject constructor(
    private val interactors: WallpaperInteractors,
//    private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    val preview = MutableStateFlow(Wallpaper())

    fun getPreviewWallpaper(wallpaperId: Int) {
        interactors.getPreview.execute(wallpaperId).onEach {
            preview.value = it.toDomain()
        }.launchIn(viewModelScope)
    }
}