package com.example.forecaster.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class WeatherWrapper(
    val list: List<Weather>,
    val city: City
)
