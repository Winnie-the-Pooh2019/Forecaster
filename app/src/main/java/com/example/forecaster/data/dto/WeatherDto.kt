package com.example.forecaster.data.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class WeatherDto(
    @PrimaryKey
    val date: String,
    val temperature: Double,
    val feelings: Double
)