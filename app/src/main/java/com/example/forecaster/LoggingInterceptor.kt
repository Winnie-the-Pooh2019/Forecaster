package com.example.forecaster

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.internal.concurrent.TaskRunner
import timber.log.Timber
import java.lang.String.format

class LoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
//        val request = chain.request()
//
//        val t1 = System.nanoTime()
//        TaskRunner.logger.info(
//            String.format(
//                "Sending request %s on %s%n%s",
//                request.url, chain.connection(), request.headers
//            )
//        )
//
//        Timber.e(String.format(
//            "Sending request %s on %s%n%s",
//            request.url, chain.connection(), request.headers
//        ))
//
//        val response = chain.proceed(request)
//
//        val t2 = System.nanoTime()
//        TaskRunner.logger.info(
//            java.lang.String.format(
//                "Received response for %s in %.1fms%n%s"
//            ))
//
//        Timber.e(String.format(
//            "Sending request %s on %s%n%s",
//            request.url, chain.connection(), request.headers
//        ))
//
//        return response

        val request: Request = chain.request()
//        val newReq: Request = request.newBuilder()
//            .addHeader("Authorization", format("token %s", githubToken))
//            .build()
        return chain.proceed(request)
    }
}