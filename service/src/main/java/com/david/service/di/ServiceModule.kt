package com.david.service.di

import com.david.service.WeatherRemoteService
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Reusable
    fun provideWeatherRemoteService(retrofit: Retrofit): WeatherRemoteService = retrofit.create()
}