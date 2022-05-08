package com.example.forecaster.model

import kotlinx.serialization.Serializable

@Serializable
data class ListItem (
    val dt: String = "",
    val main: Main,
    val weather: List<Weather>?,
    val clouds: Clouds,
    val wind: Wind,
    val visibility: String = "",
    val pop: String = "",
    val sys: Sys,
    val dt_txt: String = ""
)
