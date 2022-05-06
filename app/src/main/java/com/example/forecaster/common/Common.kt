package com.example.forecaster.common

import com.example.forecaster.LoggingInterceptor
import com.example.forecaster.retrofit.RetrofitClient
import com.example.forecaster.retrofit.RetrofitServices

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