package com.example.forecaster.ktor

import com.example.forecaster.model.ListItem
import com.example.forecaster.model.Wrapper
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import timber.log.Timber

class WeatherServiceImpl(private val client: HttpClient) : WeatherService {
    override suspend fun getWeatherList(city: String): List<ListItem> {
        return try {
            Timber.e("THREAD NAME = ${Thread.currentThread().name}")
            client.get {
                url(Routes.WEATHER_URL)
                parameter("q", city)
            }.body<Wrapper>().list
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}