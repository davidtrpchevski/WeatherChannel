package com.david.networking.interceptors

import com.david.networking.constants.OPEN_WEATHER_API_KEY
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {

    private companion object {
        private const val API_KEY_QUERY_PARAMETER = "appid"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        val newUrl = originalUrl.newBuilder()
            .addQueryParameter(API_KEY_QUERY_PARAMETER, OPEN_WEATHER_API_KEY)
            .build()

        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }
}