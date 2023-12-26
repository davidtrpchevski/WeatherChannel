package com.david.service

import com.david.networking.api_result.ApiResult
import com.david.service.models.WeatherResultModel
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherRemoteService {

    private companion object {
        private const val LATITUDE = "lat"
        private const val LONGITUDE = "lon"
    }

    @GET("weather")
    suspend fun getCurrentWeatherData(
        @Query(LATITUDE) latitude: Double?,
        @Query(LONGITUDE) longitude: Double?
    ): ApiResult<WeatherResultModel>
}