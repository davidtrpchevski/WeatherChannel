package com.david.networking.di

import com.david.networking.BuildConfig
import com.david.networking.api_result.adapter.ApiResultCallAdapterFactory
import com.david.networking.constants.OPEN_WEATHER_BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkingModule {

    @Provides
    @IntoSet
    fun provideHttpLoggingInterceptor(): Interceptor =
        HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }

    @Provides
    fun provideMoshi(): MoshiConverterFactory = MoshiConverterFactory.create().asLenient()

    @Provides
    internal fun provideOkHttp(
        interceptorSet: Set<@JvmSuppressWildcards Interceptor>,
    ): OkHttpClient = with(OkHttpClient.Builder()) {
        retryOnConnectionFailure(true)
        interceptorSet.forEach(::addInterceptor)
        build()
    }

    @Provides
    fun apiResultCallAdapterFactory(): ApiResultCallAdapterFactory = ApiResultCallAdapterFactory()

    @Provides
    @Singleton
    internal fun provideRetrofit(
        okHttpClient: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory,
        apiResultCallAdapterFactory: ApiResultCallAdapterFactory
    ): Retrofit = with(Retrofit.Builder()) {
        baseUrl(OPEN_WEATHER_BASE_URL)
        client(okHttpClient)
        addCallAdapterFactory(apiResultCallAdapterFactory)
        addConverterFactory(moshiConverterFactory)
        build()
    }
}