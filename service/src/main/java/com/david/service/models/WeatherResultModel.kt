package com.david.service.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherResultModel(
    @Json(name = "base")
    val base: String? = null,
    @Json(name = "clouds")
    val clouds: WeatherCloudsModel? = null,
    @Json(name = "cod")
    val cod: Int? = null,
    @Json(name = "coord")
    val coord: WeatherCoordModel? = null,
    @Json(name = "dt")
    val dt: Int? = null,
    @Json(name = "id")
    val id: Int? = null,
    @Json(name = "main")
    val main: WeatherMainModel? = null,
    @Json(name = "name")
    val name: String? = null,
    @Json(name = "sys")
    val sys: WeatherSysModel? = null,
    @Json(name = "timezone")
    val timezone: Int? = null,
    @Json(name = "visibility")
    val visibility: Int? = null,
    @Json(name = "weather")
    val weather: List<WeatherWeatherModel?>? = null,
    @Json(name = "wind")
    val wind: WeatherWindModel? = null
)