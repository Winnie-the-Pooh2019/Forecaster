package com.example.forecaster.model

data class City(
    val id: Int?,
    val name: String?,
    val coord: Coord?,
    val country: String?,
    val Population: Int?,
    val timezone: Int?,
    val sunrise: Int?,
    val sunset: Int?
)