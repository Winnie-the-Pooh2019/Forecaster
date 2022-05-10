package com.example.forecaster.data.model

import kotlinx.serialization.Serializable

@Serializable
data class WeatherWrapper(
    val list: List<Weather>,
)