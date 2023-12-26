package com.david.service.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherWindModel(
    @Json(name = "deg")
    val deg: Int? = null,
    @Json(name = "gust")
    val gust: Double? = null,
    @Json(name = "speed")
    val speed: Double? = null
)