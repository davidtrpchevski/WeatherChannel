package com.david.service.repository

import com.david.networking.api_result.ApiResult
import com.david.service.models.WeatherResultModel

interface WeatherRepository {
    suspend fun getWeatherByCoordinates(
        latitude: Double,
        longitude: Double
    ): ApiResult<WeatherResultModel>

    suspend fun getWeatherByCity(cityName: String): ApiResult<WeatherResultModel>
}