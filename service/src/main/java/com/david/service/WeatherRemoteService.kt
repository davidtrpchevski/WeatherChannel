package com.david.service

import com.david.networking.api_result.ApiResult
import com.david.service.models.WeatherResultModel
import com.david.service.types.TemperatureUnit
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherRemoteService {

    private companion object {
        private const val LATITUDE = "lat"
        private const val LONGITUDE = "lon"
        private const val SEARCH_QUERY = "q"
        private const val MEASUREMENT_UNITS = "units"
    }

    @GET("weather")
    suspend fun getWeather(
        @Query(LATITUDE) locationLatitude: Double? = null,
        @Query(LONGITUDE) locationLongitude: Double? = null,
        @Query(SEARCH_QUERY) searchQuery: String? = null,
        @Query(MEASUREMENT_UNITS) temperatureUnit: TemperatureUnit? = TemperatureUnit.METRIC,
    ): ApiResult<WeatherResultModel>
}