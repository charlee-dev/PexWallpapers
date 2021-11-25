package com.adwi.pexwallpapers.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adwi.pexwallpapers.util.domain.Event
import com.adwi.pexwallpapers.util.domain.Refresh
import com.adwi.pexwallpapers.util.ext.exhaustive
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class BaseViewModel : ViewModel() {

    val tag: String = javaClass.simpleName

    private val _snackBarMessage = MutableStateFlow("")
    val snackBarMessage: StateFlow<String> get() = _snackBarMessage

    private val _toastMessage = MutableStateFlow("")
    val toastMessage: StateFlow<String> get() = _toastMessage

    private val eventChannel = Channel<Event>()
    private val events = eventChannel.receiveAsFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> get() = _isRefreshing

    private var _pendingScrollToTopAfterRefresh = MutableStateFlow(false)
    val pendingScrollToTopAfterRefresh: StateFlow<Boolean> get() = _pendingScrollToTopAfterRefresh

    private val refreshTriggerChannel = Channel<Refresh>()
    val refreshTrigger = refreshTriggerChannel.receiveAsFlow()

    init {
        getEvents()
    }

    fun setEvent(event: Event) {
        viewModelScope.launch(Dispatchers.IO) {
            eventChannel.send(event)
        }
    }

    private fun getEvents() {
        viewModelScope.launch(Dispatchers.IO) {
            events.collect { event ->
                when (event) {
                    is Event.ShowErrorMessage -> {
                        Timber.tag(tag).d(
                            event.error.localizedMessage ?: "Something went wrong.. :/"
                        )
                    }
                    is Event.ShowSnackBar -> setSnackBar(event.message)
                    is Event.ShowToast -> setToast(event.message)
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
        setEvent(Event.ShowErrorMessage(throwable))
    }
}