package com.jnda.openweatherapp

import com.jnda.openweatherapp.feature.home.service.WeatherService
import com.jnda.openweatherapp.location.LocationProvider
import com.jnda.openweatherapp.location.LocationProviderImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun provideWeatherService(retrofit: Retrofit): WeatherService {
        return retrofit.create()
    }
}