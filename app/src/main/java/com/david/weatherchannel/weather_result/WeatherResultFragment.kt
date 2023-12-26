package com.david.weatherchannel.weather_result

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.david.networking.api_result.extensions.isLoading
import com.david.networking.api_result.extensions.onSuccess
import com.david.service.models.WeatherResultModel
import com.david.weatherchannel.R
import com.david.weatherchannel.binding.viewBinding
import com.david.weatherchannel.databinding.FragmentWeatherResultBinding
import com.david.weatherchannel.extensions.isKeyboardVisible
import com.david.weatherchannel.extensions.loadWeatherIcon
import com.david.weatherchannel.extensions.onQueryTextSubmit
import com.david.weatherchannel.extensions.repeatOnLifecycleStarted
import com.david.weatherchannel.weather_result.viewmodel.WeatherResultViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt

@AndroidEntryPoint
class WeatherResultFragment : Fragment(R.layout.fragment_weather_result) {

    private val binding by viewBinding(FragmentWeatherResultBinding::bind)
    private val weatherResultModel by viewModels<WeatherResultViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchBar.onQueryTextSubmit { query ->
            weatherResultModel.fetchWeatherByCity(query)
        }

        binding.searchBar.setOnQueryTextFocusChangeListener { _, hasFocus ->
            binding.searchBar.isKeyboardVisible = hasFocus
        }

        repeatOnLifecycleStarted {
            weatherResultModel.weatherResult.collect { result ->
                binding.loadingProgress.isVisible = result.isLoading
                result.onSuccess { weatherData ->
                    binding.fillWeatherData(weatherData)
                }
            }
        }
    }

    private fun FragmentWeatherResultBinding.fillWeatherData(weatherResult: WeatherResultModel) {
        cityName.text = weatherResult.name
        weatherTemperature.text = getString(
            R.string.title_current_temperature,
            weatherResult.main?.temp?.roundToInt()
        )
        weatherHigh.text = getString(
            R.string.title_high_temperature,
            weatherResult.main?.tempMax?.roundToInt()
        )
        weatherLow.text = getString(
            R.string.title_low_temperature,
            weatherResult.main?.tempMin?.roundToInt()
        )

        weatherRealFeel.text = getString(
            R.string.title_real_feel,
            weatherResult.main?.feelsLike?.roundToInt()
        )

        weatherWind.text = getString(
            R.string.title_wind_speed,
            weatherResult.wind?.speed?.roundToInt(),
        )

        weatherHumidity.text = getString(
            R.string.title_humidity,
            weatherResult.main?.humidity
        )

        val weatherData = weatherResult.weather?.firstOrNull() ?: return
        weatherTitle.text = weatherData.main
        weatherDescription.text = weatherData.description
        weatherIcon.loadWeatherIcon(weatherData.icon)
    }

}