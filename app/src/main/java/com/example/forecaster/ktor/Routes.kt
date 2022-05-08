package com.example.forecaster.ktor

object Routes {
    private const val BASE_URL = "http://api.openweathermap.org/data/2.5/"
    const val WEATHER_URL = "$BASE_URL/forecast?appid=b7eedb84f15757c5da8941a00663136d&units=metric&lang=ru"
}