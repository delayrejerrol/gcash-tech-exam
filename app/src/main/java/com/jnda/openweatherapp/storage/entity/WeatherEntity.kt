package com.jnda.openweatherapp.storage.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = WeatherEntity.TABLE_NAME)
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "weather_temp")
    val weatherTemp: String,
    @ColumnInfo(name = "date_time")
    val dateTime: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "weather_icon")
    val icon: String
) {
    companion object {
        const val TABLE_NAME = "tbl_weather"
    }
}