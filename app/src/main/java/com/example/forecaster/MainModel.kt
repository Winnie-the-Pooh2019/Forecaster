package com.example.forecaster

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.forecaster.data.MainRepository
import com.example.forecaster.data.model.Weather
import com.example.forecaster.data.retrofit.RetrofitService
import kotlinx.coroutines.runBlocking
import timber.log.Timber

class MainModel(private val city: String, application: Application) : AndroidViewModel(application) {
    val weatherList: MutableLiveData<MutableList<Weather>> by lazy {
        MutableLiveData<MutableList<Weather>>().apply {
            postValue(runBlocking {
                val data = MainRepository.getInstance(application, RetrofitService.getInstance()).getWeather(city)

                if (data != null)
                    return@runBlocking data.list.toMutableList()
                else {
                    Timber.e(IllegalArgumentException())
                    return@runBlocking mutableListOf()
                }
            })
        }
    }
}