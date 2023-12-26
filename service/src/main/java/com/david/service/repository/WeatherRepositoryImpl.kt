package com.david.service.repository

import com.david.networking.api_result.ApiResult
import com.david.service.WeatherRemoteService
import com.david.service.models.WeatherResultModel
import javax.inject.Inject

internal class WeatherRepositoryImpl @Inject constructor(private val weatherRemoteService: WeatherRemoteService) :
    WeatherRepository {
    override suspend fun getWeatherByCoordinates(
        latitude: Double,
        longitude: Double,
    ): ApiResult<WeatherResultModel> = weatherRemoteService.getWeather(
        locationLatitude = latitude,
        locationLongitude = longitude
    )

    override suspend fun getWeatherByCity(cityName: String): ApiResult<WeatherResultModel> =
        weatherRemoteService.getWeather(searchQuery = cityName)
}