package com.example.forecaster.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.forecaster.data.dao.WeatherDao
import com.example.forecaster.data.dto.WeatherDto

@Database(entities = [WeatherDto::class], version = 1)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}