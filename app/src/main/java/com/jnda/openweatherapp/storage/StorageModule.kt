package com.jnda.openweatherapp.storage

import android.content.Context
import androidx.room.Room
import com.jnda.openweatherapp.storage.dao.UserDAO
import com.jnda.openweatherapp.storage.dao.WeatherDAO
import com.jnda.openweatherapp.storage.db.AppRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class StorageModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppRoomDatabase {
        return Room.databaseBuilder(
            context,
            AppRoomDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserDao(database: AppRoomDatabase): UserDAO {
        return database.userDao()
    }

    @Provides
    @Singleton
    fun provideWeatherDao(database: AppRoomDatabase): WeatherDAO {
        return database.weatherDao()
    }
}