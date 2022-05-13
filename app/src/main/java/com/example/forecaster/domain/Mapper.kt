package com.example.forecaster.domain

import com.example.forecaster.data.entity.WeatherEntity
import com.example.forecaster.domain.model.Main
import com.example.forecaster.domain.model.Weather

object Mapper {
    fun Weather.toDto(): WeatherEntity = WeatherEntity(
        date = this.date,
        temperature = this.main.temp,
        feelings = this.main.feelsLike
    )

    fun WeatherEntity.toModel(): Weather = Weather(
        date = date,
        main = Main(
            temp = temperature,
            feelsLike = feelings
        )
    )
}