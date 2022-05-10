package com.example.forecaster.data.retrofit

import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber

class LoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder()
            .method(request.method, request.body)
            .removeHeader("Cookie")
            .removeHeader("Authorization")
            .build()

        Timber.e("Sending request ${newRequest.url} ${newRequest.headers}")

        val response = chain.proceed(newRequest)

        Timber.e("Received response for ${response.request.url}, ${response.headers}")

        return response
    }
}