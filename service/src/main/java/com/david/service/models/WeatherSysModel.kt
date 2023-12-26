package com.david.service.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherSysModel(
    @Json(name = "country")
    val country: String? = null,
    @Json(name = "id")
    val id: Int? = null,
    @Json(name = "sunrise")
    val sunrise: Int? = null,
    @Json(name = "sunset")
    val sunset: Int? = null,
    @Json(name = "type")
    val type: Int? = null
)