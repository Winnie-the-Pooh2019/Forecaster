package com.example.forecaster.retrofit

import com.example.forecaster.LoggingInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private var retrofit: Retrofit? = null

    fun getClient(baseUrl: String): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpClient
                    .Builder()
                    .addInterceptor(LoggingInterceptor())
                    .build())
                .build()
        }

        return retrofit!!
    }


}