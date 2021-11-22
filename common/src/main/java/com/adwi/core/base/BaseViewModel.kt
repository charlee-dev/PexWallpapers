package com.adwi.core.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

abstract class BaseViewModel : ViewModel() {

    val tag: String = javaClass.simpleName

    val snackBarMessage = MutableStateFlow("")
    val toastMessage = MutableStateFlow("")

    protected fun setToast(message: String) {
        toastMessage.value = message
    }

    protected fun setSnackBar(message: String) {
        snackBarMessage.value = message
    }
}