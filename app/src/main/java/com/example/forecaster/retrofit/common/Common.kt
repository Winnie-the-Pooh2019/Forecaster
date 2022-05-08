package com.example.forecaster.retrofit.common

import com.example.forecaster.ktor.LoggingInterceptor
import com.example.forecaster.retrofit.RetrofitClient
import com.example.forecaster.retrofit.RetrofitServices

@Deprecated("Use ktor instead of it")
object Common {
    private const val BASE_URL = "http://api.openweathermap.org/data/2.5/"
    val retrofitService: RetrofitServices
        get() = RetrofitClient.getClient(
            BASE_URL,
            LoggingInterceptor()
//            HttpLoggingInterceptor()
//            .apply { this.setLevel(HttpLoggingInterceptor.Level.BASIC) }
        )
            .create(RetrofitServices::class.java)
}