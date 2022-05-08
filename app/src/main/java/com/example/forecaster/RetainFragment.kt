package com.example.forecaster

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.forecaster.adapter.WeatherAdapter
import com.example.forecaster.common.Common
import com.example.forecaster.model.ListItem
import com.example.forecaster.model.Wrapper
import com.example.forecaster.retrofit.RetrofitServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.io.IOException

class RetainFragment : Fragment() {
    private val weatherAdapter: WeatherAdapter = WeatherAdapter()
    lateinit var weatherListItem: MutableList<ListItem>
    private val service: RetrofitServices by lazy {
        Common.retrofitService
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

        if (!this::weatherListItem.isInitialized)
            getForecast(view.context.getString(R.string.city))
        else
            weatherAdapter.submitList(weatherListItem)
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.e("FRAGMENT DESTROYED")
    }

    private fun getForecast(city: String) {
        service.getWeather(city).enqueue(object : Callback<Wrapper> {
            override fun onResponse(call: Call<Wrapper>, response: Response<Wrapper>) {
                try {
                    val data = response.body() as Wrapper
                    weatherListItem = data.list.toMutableList()

                    weatherAdapter.submitList(weatherListItem)
                } catch (e: IOException) {
                    Timber.e(e)
                }
            }

            override fun onFailure(call: Call<Wrapper>, t: Throwable) {
                Timber.e("CANNOT ESTABLISH CONNECTION TO API")
                weatherAdapter.submitList(mutableListOf<ListItem>())
            }
        })
    }
}