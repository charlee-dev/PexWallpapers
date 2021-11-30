package com.adwi.components.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adwi.components.ext.exhaustive
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class BaseViewModel : ViewModel() {

    val tag: String = javaClass.simpleName

    private var _pendingScrollToTopAfterRefresh = MutableStateFlow(false)
    private var _isRefreshing = MutableStateFlow(false)
    private val _snackBarMessage = MutableStateFlow("")
    private val _toastMessage = MutableStateFlow("")

    val pendingScrollToTopAfterRefresh = _pendingScrollToTopAfterRefresh.asStateFlow()
    val isRefreshing = _isRefreshing.asStateFlow()
    val snackBarMessage = _snackBarMessage.asStateFlow()
    val toastMessage = _toastMessage.asStateFlow()

    private val eventChannel = Channel<com.adwi.core.Event>()
    private val refreshTriggerChannel = Channel<Refresh>()

    private val events = eventChannel.receiveAsFlow()
    val refreshTrigger = refreshTriggerChannel.receiveAsFlow()

    init {
        getEvents()
    }

    fun setEvent(event: com.adwi.core.Event) {
        viewModelScope.launch(Dispatchers.IO) {
            eventChannel.send(event)
        }
    }

    private fun getEvents() {
        viewModelScope.launch(Dispatchers.IO) {
            events.collect { event ->
                when (event) {
                    is com.adwi.core.Event.ShowErrorMessage -> {
                        Timber.tag(tag).d(
                            event.error.localizedMessage ?: "Something went wrong.. :/"
                        )
                    }
                    is com.adwi.core.Event.ShowSnackBar -> setSnackBar(event.message)
                    is com.adwi.core.Event.ShowToast -> setToast(event.message)
                }.exhaustive
            }
        }
    }

    private fun setToast(message: String) {
        _toastMessage.value = message
    }

    fun setSnackBar(message: String) {
        _snackBarMessage.value = message
    }

    fun setIsRefreshing(refresh: Boolean) {
        _isRefreshing.value = refresh
    }

    fun setRefreshTriggerChannel(refresh: Refresh) {
        viewModelScope.launch {
            refreshTriggerChannel.send(refresh)
        }
    }

    fun setPendingScrollToTopAfterRefresh(scroll: Boolean) {
        viewModelScope.launch {
            _pendingScrollToTopAfterRefresh.value = scroll
        }
    }

    protected fun onFetchSuccess() {
        Timber.tag(tag).d("onFetchSuccess")
        _pendingScrollToTopAfterRefresh.value = true
        setIsRefreshing(false)
    }

    protected fun onFetchFailed(throwable: Throwable) {
        Timber.tag(tag).d("onFetchFailed")
        setIsRefreshing(false)
        setEvent(com.adwi.core.Event.ShowErrorMessage(throwable))
    }
}

enum class Refresh {
    FORCE, NORMAL
}