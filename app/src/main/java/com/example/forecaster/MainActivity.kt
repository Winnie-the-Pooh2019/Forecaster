package com.example.forecaster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.forecaster.common.Common
import com.example.forecaster.model.Wrapper
import com.example.forecaster.retrofit.RetrofitServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private val service: RetrofitServices by lazy {
        Common.retrofitService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))

        getForecast()
    }

    private fun getForecast() {
        service.getWeather("Moscow").enqueue(object : Callback<Wrapper> {
            override fun onResponse(call: Call<Wrapper>, response: Response<Wrapper>) {
                try {
                    val data = response.body() as Wrapper
                    val listWeather = data.list
                    println("Logging list $listWeather")
                    Timber.tag("LOGGING DATA").w(data.toString())
                    println("LOGGING DATA = $data")
                } catch (e: IOException) {
                    Timber.e("DATA COLLECTED BUT SMTH GOT WRONG")
                    Timber.e(e)
                    println("DATA COLLECTED BUT SMTH GOT WRONG")
                }
            }

            override fun onFailure(call: Call<Wrapper>, t: Throwable) {
                Timber.e("CANNOT ESTABLISH CONNECTION TO API")
                println("CANNOT ESTABLISH CONNECTION TO API")
            }
        })
    }
}