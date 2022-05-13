package com.example.forecaster.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.forecaster.data.MainRepository

class MainViewModelFactory(
    private val city: String,
    private val repo: MainRepository,
    private val activity: AppCompatActivity
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java))
            return MainViewModel(city, repo, activity) as T
        throw IllegalArgumentException()
    }
}