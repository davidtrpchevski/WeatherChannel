package com.david.weatherchannel.extensions

import android.widget.ImageView
import coil.load
import coil.request.ImageRequest
import com.david.networking.constants.OPEN_WEATHER_API_IMAGE_URL

fun ImageView.loadWeatherIcon(
    imagePath: String?,
    builder: ImageRequest.Builder.() -> Unit = {}
) {
    val url = "$OPEN_WEATHER_API_IMAGE_URL/$imagePath@4x.png"
    load(url, builder = builder)
}