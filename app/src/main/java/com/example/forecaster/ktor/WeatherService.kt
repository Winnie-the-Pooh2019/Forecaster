package com.example.forecaster.ktor

import com.example.forecaster.model.ListItem
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.gson.*

interface WeatherService {
    suspend fun getWeatherList(city: String): List<ListItem>

    companion object {
        fun create(): WeatherService {
            return WeatherServiceImpl(client = HttpClient(OkHttp) {
                engine {
                    addInterceptor(LoggingInterceptor())
                }
                install(ContentNegotiation) {
                    gson()
                }
            })
        }
    }
}