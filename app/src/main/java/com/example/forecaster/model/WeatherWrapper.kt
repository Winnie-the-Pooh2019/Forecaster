package com.example.forecaster.model

import kotlinx.serialization.Serializable

@Serializable
data class WeatherWrapper(
    val list: List<Weather>,
)
