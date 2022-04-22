package com.example.forecaster.model

data class Wrapper(
    val cod: String?,
    val message: Int,
    val cnt: Int?,
    val itemList: ItemList?,
    val city: City?
)
