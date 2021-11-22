package com.adwi.core.domain

sealed class Event {
    data class ShowErrorMessage(val error: Throwable) : Event()
}