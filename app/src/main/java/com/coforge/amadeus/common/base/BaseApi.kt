package com.coforge.amadeus.common.base

import com.coforge.amadeus.network.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import java.io.IOException

/*
* Base Class For Safe Network Call
* API's calling will happen on IO Thread
* */
abstract class BaseApi {

    suspend fun <T> safeApiCall(
        dispatcher: CoroutineDispatcher = Dispatchers.IO, apiCall: suspend () -> Response<T>
    ): Flow<ResultWrapper<T>> = flow {
        emit(ResultWrapper.Loading)
        val response = apiCall()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                emit(ResultWrapper.Success(body))
            } else {
                val error = response.errorBody()
                if (error != null) {
                    emit(ResultWrapper.Error(IOException(error.toString())))
                } else {
                    emit(ResultWrapper.Error(IOException("something went wrong")))
                }
            }
        } else {
            emit(ResultWrapper.Error(Throwable(response.errorBody().toString())))
        }

    }.catch { e ->
        e.printStackTrace()
        emit(ResultWrapper.Error(Throwable(e.localizedMessage)))
    }.flowOn(dispatcher)
}