package com.david.networking.api_result.adapter

import com.david.networking.api_result.ApiResult
import com.david.networking.api_result.api_call.ApiResultCall
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

internal class ApiResultAdapter(private val resultType: Type) :
    CallAdapter<Type, Call<ApiResult<Type>>> {

    override fun responseType(): Type = resultType

    override fun adapt(call: Call<Type>): Call<ApiResult<Type>> = ApiResultCall(call)
}