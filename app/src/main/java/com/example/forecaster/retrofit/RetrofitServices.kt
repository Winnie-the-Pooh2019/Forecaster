package com.example.forecaster.retrofit

import com.example.forecaster.model.Wrapper
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitServices {
    @GET("forecast?appid=b7eedb84f15757c5da8941a00663136d&units=metric&lang=ru")
    fun getWeather(@Query("q") name: String): Call<Wrapper>
}