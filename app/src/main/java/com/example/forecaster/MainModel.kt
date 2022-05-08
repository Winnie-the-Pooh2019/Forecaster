package com.example.forecaster

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.forecaster.ktor.WeatherService
import com.example.forecaster.model.ListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class MainModel(private val city: String) : ViewModel() {
    val weatherList: MutableLiveData<MutableList<ListItem>> by lazy {
        MutableLiveData<MutableList<ListItem>>().apply {
            value = runBlocking {
                return@runBlocking withContext(Dispatchers.IO) {
                    WeatherService.create().getWeatherList(city).toMutableList()
                }
            }
        }
    }
}