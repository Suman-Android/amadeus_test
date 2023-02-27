package com.coforge.amadeus.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform


sealed class ResultWrapper<out T> {
    object Loading : ResultWrapper<Nothing>()
    data class Success<T>(val data: T) : ResultWrapper<T>()
    data class Error(val msg: Throwable) : ResultWrapper<Nothing>()
}


fun <T, R> ResultWrapper<T>.map(transform: (T) -> R): ResultWrapper<R> {
    return when (this) {
        is ResultWrapper.Success -> ResultWrapper.Success(transform(data))
        is ResultWrapper.Error -> ResultWrapper.Error(msg)
        ResultWrapper.Loading -> ResultWrapper.Loading
    }
}

fun <T> Flow<ResultWrapper<T>>.doOnSuccess(action: suspend (T) -> Unit): Flow<ResultWrapper<T>> =
    transform { result ->
        if (result is ResultWrapper.Success) {
            action(result.data)
        }
        return@transform emit(result)
    }


fun <T> Flow<ResultWrapper<T>>.doOnFailure(action: suspend (Throwable?) -> Unit): Flow<ResultWrapper<T>> =
    transform { result ->
        if (result is ResultWrapper.Error) {
            action(result.msg)
        }
        return@transform emit(result)
    }

fun <T> Flow<ResultWrapper<T>>.doOnLoading(action: suspend () -> Unit): Flow<ResultWrapper<T>> =
    transform { result ->
        if (result is ResultWrapper.Loading) {
            action()
        }
        return@transform emit(result)
    }
