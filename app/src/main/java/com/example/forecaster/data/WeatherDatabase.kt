package com.example.forecaster.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.forecaster.data.dao.WeatherDao
import com.example.forecaster.data.entity.WeatherEntity

@Database(entities = [WeatherEntity::class], version = 1)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}