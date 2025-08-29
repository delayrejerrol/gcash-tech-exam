package com.jnda.openweatherapp.storage.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jnda.openweatherapp.storage.dao.UserDAO
import com.jnda.openweatherapp.storage.dao.WeatherDAO
import com.jnda.openweatherapp.storage.entity.UserEntity
import com.jnda.openweatherapp.storage.entity.WeatherEntity

@Database(
    entities = [
        UserEntity::class,
        WeatherEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppRoomDatabase : RoomDatabase() {
    abstract fun userDao(): UserDAO
    abstract fun weatherDao(): WeatherDAO
}