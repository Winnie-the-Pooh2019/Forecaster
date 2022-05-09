package com.example.forecaster

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.forecaster.model.ListItem
import com.example.forecaster.retrofit.MainRepository
import kotlinx.coroutines.runBlocking
import timber.log.Timber

class MainModel(private val city: String) : ViewModel() {
    val weatherList: MutableLiveData<MutableList<ListItem>> by lazy {
        MutableLiveData<MutableList<ListItem>>().apply {
            value = runBlocking {
                val data = MainRepository.repository.getWeather(city)

                if (data != null)
                    return@runBlocking data.list.toMutableList()
                else {
                    Timber.e(IllegalArgumentException())
                    return@runBlocking mutableListOf()
                }
            }
        }
    }
}