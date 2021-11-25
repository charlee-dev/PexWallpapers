package com.adwi.pexwallpapers.ui.main

import androidx.paging.ExperimentalPagingApi
import com.adwi.core.IoDispatcher
import com.adwi.core.base.BaseViewModel
import com.adwi.shared.work.WorkTools
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@HiltViewModel
class MainViewModel @ExperimentalCoroutinesApi
@ExperimentalPagingApi
@Inject constructor(
    private val workTools: WorkTools,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel() {

}