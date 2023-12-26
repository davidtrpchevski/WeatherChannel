package com.david.weatherchannel.location

import android.content.Context
import android.location.LocationManager
import com.david.weatherchannel.extensions.locationManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object LocationModule {

    @Provides
    fun provideLocationManager(@ApplicationContext context: Context): LocationManager =
        context.locationManager
}