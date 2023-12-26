package com.david.weatherchannel.weather_result.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.networking.api_result.ApiResult
import com.david.service.models.WeatherResultModel
import com.david.service.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherResultViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _weatherResult = MutableStateFlow<ApiResult<WeatherResultModel>>(ApiResult.Idle)
    val weatherResult = _weatherResult.asStateFlow()

    init {
        fetchWeatherByCity("London")
    }

    fun fetchWeatherByCity(cityName: String) {
        _weatherResult.getAndUpdate { ApiResult.Loading }
        viewModelScope.launch {
            _weatherResult.getAndUpdate { weatherRepository.getWeatherByCity(cityName) }
        }
    }

    fun fetchWeatherByCoordinates(latitude: Double, longitude: Double) {
        _weatherResult.getAndUpdate { ApiResult.Loading }
        viewModelScope.launch {
            _weatherResult.getAndUpdate {
                weatherRepository.getWeatherByCoordinates(latitude, longitude)
            }
        }
    }
}