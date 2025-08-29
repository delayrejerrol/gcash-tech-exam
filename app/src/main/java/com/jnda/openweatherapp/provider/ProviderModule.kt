package com.jnda.openweatherapp.provider

import com.jnda.openweatherapp.location.LocationProvider
import com.jnda.openweatherapp.location.LocationProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ProviderModule {
    @Binds
    @Singleton
    abstract fun bindWeatherAPIProvider(
        weatherAPIProviderImpl: WeatherAPIProviderImpl
    ): WeatherAPIProvider

    @Binds
    @Singleton
    abstract fun bindLocationProvider(
        repository: LocationProviderImpl
    ): LocationProvider
}