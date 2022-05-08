package com.example.forecaster

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.forecaster.adapter.WeatherAdapter
import com.example.forecaster.ktor.WeatherService
import com.example.forecaster.model.ListItem
import com.example.forecaster.model.Wrapper
import com.example.forecaster.retrofit.RetrofitServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.io.IOException

class RetainFragment : Fragment() {
    private val weatherAdapter: WeatherAdapter = WeatherAdapter()

    private val weatherList: MutableList<ListItem> by lazy {
        runBlocking {
            return@runBlocking withContext(Dispatchers.IO) {
                WeatherService.create().getWeatherList("Moscow").toMutableList()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_retain, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.e("FRAGMENT VIEW CREATED")

        view.findViewById<RecyclerView>(R.id.recycler_vew).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = weatherAdapter
        }

        weatherAdapter.submitList(weatherList)
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.e("FRAGMENT DESTROYED")
    }

    @Deprecated("Use ktor with coroutines instead")
    private fun getForecast(city: String, adapter: WeatherAdapter, service: RetrofitServices) {
        service.getWeather(city).enqueue(object : Callback<Wrapper> {
            override fun onResponse(call: Call<Wrapper>, response: Response<Wrapper>) {
                try {
                    val data = response.body() as Wrapper
                    val weatherListItem = data.list.toMutableList()

                    adapter.submitList(weatherListItem)
                } catch (e: IOException) {
                    Timber.e(e)
                }
            }

            override fun onFailure(call: Call<Wrapper>, t: Throwable) {
                Timber.e("CANNOT ESTABLISH CONNECTION TO API")
                adapter.submitList(mutableListOf<ListItem>())
            }
        })
    }
}