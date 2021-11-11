package com.adwi.core.util

import com.adwi.core.domain.DataState
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
            query().collect { send(DataState.Success(it)) }
        } catch (t: Throwable) {
            loading.cancel()
            query().collect { send(DataState.Error(t, it)) }
        }
    } else {
        query().collect { send(DataState.Success(it)) }
    }
}