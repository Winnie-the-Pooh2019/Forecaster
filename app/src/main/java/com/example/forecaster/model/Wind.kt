package com.example.forecaster.model

import kotlinx.serialization.Serializable

@Serializable
data class Wind(
    val deg: Int = 0,
    val speed: Double = 0.0,
    val gust: Double = 0.0
)
