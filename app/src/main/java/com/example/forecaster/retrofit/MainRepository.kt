package com.example.forecaster.retrofit

import com.example.forecaster.model.WeatherWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainRepository private constructor(private val service: RetrofitService) {
    suspend fun getWeather(name: String): WeatherWrapper? = withContext(Dispatchers.IO) {
        val response = service.getWeather(name)

        return@withContext if (response.isSuccessful)
            response.body()
        else null
    }

    companion object {
        val repository: MainRepository by lazy {
            MainRepository(RetrofitService.getInstance())
        }
    }
}