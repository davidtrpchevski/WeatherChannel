package com.david.networking.api_result

import okhttp3.ResponseBody

sealed interface ApiResult<out T> {
    data object Idle : ApiResult<Nothing>
    data object Loading : ApiResult<Nothing>
    data class Success<T>(val data: T) : ApiResult<T>
    data class ApiError(val errorBody: ResponseBody?, val errorCode: Int) : ApiResult<Nothing>
    data class Error(val throwable: Throwable) : ApiResult<Nothing>
}
