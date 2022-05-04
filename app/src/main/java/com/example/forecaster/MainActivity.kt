package com.example.forecaster

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.forecaster.adapter.WeatherAdapter
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
        setTitle(R.string.city)
        Timber.plant(Timber.DebugTree())

        val weatherAdapter = WeatherAdapter()
        findViewById<RecyclerView>(R.id.recycler_vew).apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = weatherAdapter
        }

        getForecast(getString(R.string.city), weatherAdapter)
    }

    private fun getForecast(city: String, adapter: WeatherAdapter) {
        service.getWeather(city).enqueue(object : Callback<Wrapper> {
            override fun onResponse(call: Call<Wrapper>, response: Response<Wrapper>) {
                try {
                    val data = response.body() as Wrapper
                    val listWeather = data.list
                    println("Logging list $listWeather")
                    Timber.tag("LOGGING DATA").w(data.toString())
                    println("LOGGING DATA = $data")

                    adapter.submitList(listWeather)
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