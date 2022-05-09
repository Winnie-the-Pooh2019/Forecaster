package com.example.forecaster.retrofit

import com.example.forecaster.ktor.LoggingInterceptor
import com.example.forecaster.model.Wrapper
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("forecast?appid=b7eedb84f15757c5da8941a00663136d&units=metric&lang=ru")
    suspend fun getWeather(@Query("q") name: String): Response<Wrapper>

    companion object {
        private const val BASE_URL = "http://api.openweathermap.org/data/2.5/"

        fun getInstance(): RetrofitService = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient
                .Builder()
                .addInterceptor(LoggingInterceptor())
                .build())
            .build().create(RetrofitService::class.java)
    }
}