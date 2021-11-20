package com.adwi.common.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import timber.log.Timber

abstract class BaseViewModel : ViewModel() {

    val tag: String = javaClass.simpleName

    val snackBarMessage = MutableStateFlow("")
    val toastMessage = MutableStateFlow("")

    init {
        Timber.tag(tag).d("Initialized")
    }

//    private val _loading = MutableStateFlow(LoadingState.NOT_LOADING)
//    val loading: StateFlow<LoadingState> = _loading
//
//    fun setLoading(loadingState: LoadingState) {
//        _loading.value = loadingState
//    }

    fun setToast(message: String) {
        toastMessage.value = message
    }

    fun setSnackBar(message: String) {
        snackBarMessage.value = message
    }
}

enum class LoadingState { NOT_LOADING, LOADING, SUCCESS }

enum class Refresh {
    FORCE, NORMAL
}

sealed class Event {
    data class ShowErrorMessage(val error: Throwable) : Event()
}