package com.adwi.interactors.common

import com.adwi.core.domain.DataState
import com.adwi.core.domain.UIComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


// Cold flow - executed only if there is collector
@ExperimentalCoroutinesApi
inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
) = channelFlow {
    val data = query().first()

    if (shouldFetch(data)) {
        val loading = launch {
            query().collect { send(DataState.Loading(it)) }
        }
        try {
            delay(2000)
            saveFetchResult(fetch())
            loading.cancel()
            query().collect { send(DataState.Data(it)) }
        } catch (t: Throwable) {
            loading.cancel()
            query().collect {
                send(
                    DataState.Response(
                        uiComponent = UIComponent.Dialog(it.toString()),
                        it
                    )
                )
            }
        }
    } else {
        query().collect { send(DataState.Data(it)) }
    }
}