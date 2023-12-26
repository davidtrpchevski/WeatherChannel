package com.david.service.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherCloudsModel(
    @Json(name = "all")
    val all: Int? = null
)