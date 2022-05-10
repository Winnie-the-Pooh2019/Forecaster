package com.example.forecaster

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.forecaster.data.MainRepository
import com.example.forecaster.data.model.Weather
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.runBlocking

class MainModel(private val city: String, private val repository: MainRepository, activity: AppCompatActivity) :
    AndroidViewModel(activity.application) {

    val weatherList: MutableLiveData<MutableList<Weather>> by lazy {
        MutableLiveData<MutableList<Weather>>().apply {
            postValue(runBlocking {
                val data = repository.getWeatherFromNet(city)

                return@runBlocking if (data.list.isEmpty()) {
                    Snackbar.make(
                        activity.findViewById(R.id.main_layout),
                        R.string.connection_failed,
                        Snackbar.LENGTH_SHORT
                    ).show()

                    repository.getWeatherFromDb(city).list.toMutableList()
                } else
                    data.list.toMutableList().apply {
                        sortWith { left, right -> left.date.compareTo(right.date) }
                    }
            })
        }
    }
}