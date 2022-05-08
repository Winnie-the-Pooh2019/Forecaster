package com.example.forecaster.model

import kotlinx.serialization.Serializable

@Serializable
data class Wrapper(
    val cod: String = "",
    val message: Int = 0,
    val cnt: Int = 0,
    val list: List<ListItem>,
    val city: City
) {
    override fun toString(): String {
        return "Wrapper(cod=$cod, message=$message, cnt=$cnt, itemList=$list, city=$city)"
    }
}
