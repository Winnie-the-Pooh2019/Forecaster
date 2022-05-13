package com.example.forecaster.data.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class WeatherDto(
    @PrimaryKey
    val date: String,
    val temperature: Double,
    val feelings: Double,
    val day: String
) {
    constructor(date: String, temperature: Double, feelings: Double)
            : this(date, temperature, feelings, date.toDay())

    companion object {
        fun String.toDay(): String {
            val index = this.indexOf(' ')
            return this.substring(0, if (index != -1) index else this.length - 1)
        }
    }
}