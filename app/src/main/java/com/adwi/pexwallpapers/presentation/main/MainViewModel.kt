package com.adwi.pexwallpapers.presentation.main

import androidx.paging.ExperimentalPagingApi
import com.adwi.pexwallpapers.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@HiltViewModel
class MainViewModel @ExperimentalCoroutinesApi
@ExperimentalPagingApi
@Inject constructor() : BaseViewModel()