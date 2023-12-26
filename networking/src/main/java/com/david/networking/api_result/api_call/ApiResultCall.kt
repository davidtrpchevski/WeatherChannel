package com.david.networking.api_result.api_call

import com.david.networking.api_result.ApiResult
import com.david.networking.api_result.extensions.unwrapResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class ApiResultCall<T : Any>(private val proxy: Call<T>) :
    ApiResultCallDelegate<T, ApiResult<T>>(proxy) {

    override fun cloneImpl(): Call<ApiResult<T>> = ApiResultCall(proxy.clone())

    override fun enqueueImpl(callback: Callback<ApiResult<T>>) {
        proxy.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                callback.onResponse(this@ApiResultCall, Response.success(response.unwrapResponse()))
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                callback.onResponse(this@ApiResultCall, Response.success(ApiResult.Error(t)))
            }
        })
    }
}