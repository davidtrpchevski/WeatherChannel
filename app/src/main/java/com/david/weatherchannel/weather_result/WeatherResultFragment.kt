package com.david.weatherchannel.weather_result

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.david.networking.api_result.extensions.isLoading
import com.david.weatherchannel.R
import com.david.weatherchannel.binding.viewBinding
import com.david.weatherchannel.databinding.FragmentWeatherResultBinding
import com.david.weatherchannel.extensions.repeatOnLifecycleStarted
import com.david.weatherchannel.weather_result.viewmodel.WeatherResultViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherResultFragment : Fragment(R.layout.fragment_weather_result) {

    private val binding by viewBinding(FragmentWeatherResultBinding::bind)
    private val weatherResultModel by viewModels<WeatherResultViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        repeatOnLifecycleStarted {
            weatherResultModel.weatherResult.collect { result ->
                binding.loadingProgress.isVisible = result.isLoading
            }
        }
    }

}