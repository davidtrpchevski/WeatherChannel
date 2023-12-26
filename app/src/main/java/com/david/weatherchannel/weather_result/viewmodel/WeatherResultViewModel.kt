package com.david.weatherchannel.weather_result.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.networking.api_result.ApiResult
import com.david.service.models.WeatherResultModel
import com.david.service.repository.WeatherRepository
import com.david.weatherchannel.location.CurrentLocationProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherResultViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val currentLocationProvider: CurrentLocationProvider
) : ViewModel() {

    private val _weatherResult = MutableStateFlow<ApiResult<WeatherResultModel>>(ApiResult.Idle)
    val weatherResult = _weatherResult.asStateFlow()

    fun getCurrentLocation() {
        viewModelScope.launch {
            val myLocation = currentLocationProvider.getCurrentLocation()
            fetchWeatherByCoordinates(myLocation.latitude, myLocation.longitude)
        }
    }

    fun fetchWeatherByCity(cityName: String) {
        _weatherResult.getAndUpdate { ApiResult.Loading }
        viewModelScope.launch {
            _weatherResult.getAndUpdate { weatherRepository.getWeatherByCity(cityName) }
        }
    }

    private fun fetchWeatherByCoordinates(latitude: Double, longitude: Double) {
        _weatherResult.getAndUpdate { ApiResult.Loading }
        viewModelScope.launch {
            _weatherResult.getAndUpdate {
                weatherRepository.getWeatherByCoordinates(latitude, longitude)
            }
        }
    }
}