package com.david.service.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherWeatherModel(
    @Json(name = "description")
    val description: String? = null,
    @Json(name = "icon")
    val icon: String? = null,
    @Json(name = "id")
    val id: Int? = null,
    @Json(name = "main")
    val main: String? = null
)