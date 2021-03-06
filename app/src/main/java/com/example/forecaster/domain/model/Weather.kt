package com.example.forecaster.domain.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Weather (
    val main: Main,
    @SerializedName("dt_txt")
    val date: String
)
