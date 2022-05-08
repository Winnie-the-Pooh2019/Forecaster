package com.example.forecaster.model

import kotlinx.serialization.Serializable

@Serializable
data class Coord(
    val lon: Double = 0.0,
    val lat: Double = 0.0
)
