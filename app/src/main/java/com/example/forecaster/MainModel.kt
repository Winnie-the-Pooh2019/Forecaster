package com.example.forecaster

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.forecaster.data.MainRepository
import com.example.forecaster.data.model.Weather
import com.example.forecaster.data.retrofit.RetrofitService
import kotlinx.coroutines.runBlocking

class MainModel(private val city: String, private val service: RetrofitService, application: Application) : AndroidViewModel(application) {
    val weatherList: MutableLiveData<MutableList<Weather>> by lazy {
        MutableLiveData<MutableList<Weather>>().apply {
            postValue(runBlocking {
                val data = MainRepository.getInstance(application, service).getWeather(city)

                if (data.list.isNotEmpty())
                    Toast.makeText(
                        application.applicationContext,
                        application.getString(R.string.connection_failed),
                        Toast.LENGTH_SHORT
                    )

                return@runBlocking data.list.toMutableList().apply {
                    sortWith { left, right -> left.date.compareTo(right.date) }
                }
            })
        }
    }
}