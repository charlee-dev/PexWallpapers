package com.adwi.core

sealed class Event {
    data class ShowErrorMessage(val error: Throwable) : Event()
    data class ShowSnackBar(val message: String) : Event()
    data class ShowToast(val message: String) : Event()
}