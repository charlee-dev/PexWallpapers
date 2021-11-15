package com.adwi.core.domain

sealed class DataState<T>(
    val data: T? = null,
    val error: Throwable? = null
) {
    class Data<T>(
        data: T
    ) : DataState<T>(data)

    class Loading<T>(
        data: T? = null,
        val loadingState: LoadingState = LoadingState.Idle
    ) : DataState<T>(data)

    class Response<T>(
        val uiComponent: UIComponent,
        data: T? = null
    ) : DataState<T>(data)
}