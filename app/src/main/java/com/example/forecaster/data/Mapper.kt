package com.example.forecaster.data

import com.example.forecaster.data.dto.WeatherDto
import com.example.forecaster.data.model.Weather

object Mapper {
    fun Weather.toDto(): WeatherDto = WeatherDto(
        date = this.date,
        temperature = this.main.temp,
        feelings = this.main.feelsLike
    )
}