package com.david.networking.di

import com.david.networking.BuildConfig
import com.david.networking.interceptors.ApiKeyInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor

@Module
@InstallIn(SingletonComponent::class)
object InterceptorsModule {

    @Provides
    @IntoSet
    fun provideHttpLoggingInterceptor(): Interceptor =
        HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }

    @Provides
    @IntoSet
    fun provideApiKeyInterceptor(): Interceptor = ApiKeyInterceptor()
}