package com.example.forecaster

import androidx.lifecycle.ViewModel
import com.example.forecaster.ktor.WeatherService
import com.example.forecaster.model.ListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class MainModel : ViewModel() {
    val weatherList: List<ListItem> by lazy {
        runBlocking {
            return@runBlocking withContext(Dispatchers.IO) {
                WeatherService.create().getWeatherList("Moscow")
            }
        }
    }
}