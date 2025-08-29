package com.jnda.openweatherapp

import com.jnda.openweatherapp.feature.home.repository.WeatherRepository
import com.jnda.openweatherapp.feature.home.repository.WeatherRepositoryImpl
import com.jnda.openweatherapp.feature.login.repository.LoginRepository
import com.jnda.openweatherapp.feature.login.repository.LoginRepositoryImpl
import com.jnda.openweatherapp.feature.register.repository.RegisterRepository
import com.jnda.openweatherapp.feature.register.repository.RegisterRepositoryImpl
import com.jnda.openweatherapp.location.LocationProvider
import com.jnda.openweatherapp.location.LocationProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindLoginRepository(
        repository: LoginRepositoryImpl
    ): LoginRepository

    @Binds
    abstract fun bindRegisterRepository(
        repository: RegisterRepositoryImpl
    ): RegisterRepository

    @Binds
    abstract fun bindWeatherRepository(
        repository: WeatherRepositoryImpl
    ): WeatherRepository

}