package com.example.forecaster.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class WeatherEntity(
    @PrimaryKey
    val date: String = "",
    val temperature: Double,
    val feelings: Double,
    val day: String = date.toDay()
)

private fun String.toDay(): String {
    val index = this.indexOf(' ')
    return this.substring(0, if (index != -1) index else this.length - 1)
}