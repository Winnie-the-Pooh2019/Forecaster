package com.example.forecaster.model

data class ItemList(
    val dt: String?,
    val main: Main,
    val weather: List<Weather>?,
    val clouds: Clouds,
    val wind: Wind?,
    val visibility: Int?,
    val pop: Int?,
    val sys: Sys,
    val dt_txt: String?
)
