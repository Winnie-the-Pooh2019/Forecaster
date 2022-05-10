package com.example.forecaster.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Main(
    val temp: Double,
    @SerializedName("feels_like")
    val feelsLike: Double,
)
