package com.example.forecaster.model

import kotlinx.serialization.Serializable

@Serializable
data class Weather(
    val icon: String = "",
    val description: String = "",
    val main: String = "",
    val id: Int = 0
)
