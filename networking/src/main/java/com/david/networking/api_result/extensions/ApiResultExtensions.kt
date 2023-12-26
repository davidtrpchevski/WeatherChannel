package com.david.networking.api_result.extensions


import com.david.networking.api_result.ApiResult
import okhttp3.ResponseBody
import retrofit2.Response

fun <T> Response<T>.unwrapResponse(): ApiResult<T> =
    try {
        val data = body()
        if (isSuccessful && data != null) {
            ApiResult.Success(data)
        } else {
            ApiResult.ApiError(errorBody(), code())
        }
    } catch (throwable: Throwable) {
        ApiResult.Error(throwable)
    }

inline fun <T> ApiResult<T>.onLoading(function: () -> Unit): ApiResult<T> {
    if (this is ApiResult.Loading) function()

    return this
}

inline fun <T> ApiResult<T>.onIdle(function: () -> Unit): ApiResult<T> {
    if (this is ApiResult.Idle) function()

    return this
}

inline fun <T> ApiResult<T>.onError(function: (throwable: Throwable) -> Unit): ApiResult<T> {
    if (this is ApiResult.Error) {
        function(throwable)
    }

    return this
}

inline fun <T> ApiResult<T>.onApiError(function: (errorBody: ResponseBody?, responseCode: Int) -> Unit): ApiResult<T> {
    if (this is ApiResult.ApiError) {
        function(errorBody, errorCode)
    }

    return this
}

inline fun <T> ApiResult<T>.onSuccess(function: (model: T) -> Unit): ApiResult<T> {
    if (this is ApiResult.Success) {
        function(data)
    }

    return this
}