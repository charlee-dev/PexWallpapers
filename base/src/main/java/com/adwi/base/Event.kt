package com.adwi.base

sealed class Event {
    data class ShowErrorMessage(val error: Throwable) : Event()
}