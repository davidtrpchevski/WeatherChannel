package com.david.service.di

import com.david.service.WeatherRemoteService
import com.david.service.repository.WeatherRepository
import com.david.service.repository.WeatherRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
internal abstract class WeatherServiceModule {

    @Binds
    abstract fun bindWeatherRepository(weatherRepositoryImpl: WeatherRepositoryImpl): WeatherRepository

    companion object {
        @Provides
        @Reusable
        fun provideWeatherRemoteService(retrofit: Retrofit): WeatherRemoteService =
            retrofit.create()
    }
}