package com.david.weatherchannel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.david.weatherchannel.binding.viewBinding
import com.david.weatherchannel.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}