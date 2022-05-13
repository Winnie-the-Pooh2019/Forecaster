package com.example.forecaster

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.forecaster.data.MainRepository
import com.example.forecaster.ui.model.Weather
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val city: String, private val repository: MainRepository, activity: AppCompatActivity) :
    AndroidViewModel(activity.application) {

    private var _liveData = MutableLiveData<List<Weather>>()
    val liveDate: LiveData<List<Weather>>
        get() = _liveData

    init {
        CoroutineScope(Dispatchers.Default).launch {
            val data = repository.getWeatherFromNet(city)

            if (data.list.isEmpty()) {
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

            _liveData.postValue(data.list.toMutableList())
        }
    }
}