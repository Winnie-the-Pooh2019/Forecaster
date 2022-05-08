package com.example.forecaster

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.forecaster.adapter.WeatherAdapter
import timber.log.Timber
import java.io.File

class MainActivity : AppCompatActivity() {

    /*
     * viewModels assures persistence of model state.
     * if the activity is redrawn, the viewModel looks for an already existing instance
     */
    private val model: MainModel by viewModelsFactory { MainModel(getString(R.string.city)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        setTitle(R.string.city)
        Timber.plant(Timber.DebugTree())

        // todo check this feature via physical phone
        try {
            val filename = "logcat_${System.currentTimeMillis()}.txt"
            val outputFile = File(this.externalCacheDir, filename)
            Runtime.getRuntime().exec("logcat -f ${outputFile.absolutePath}")
        } catch (e: Exception) {
            Timber.e(e)
        }
        Timber.e("ACTIVITY CREATED")

        findViewById<RecyclerView>(R.id.recycler_vew).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = WeatherAdapter().apply {
                model.weatherList.observe(this@MainActivity) {
                    this.submitList(it)
                }
            }
        }
    }

    /*
     * proprietary factory of viewModel gives ability putting params into constructor
     */
    @Suppress("UNCHECKED_CAST")
    private inline fun <reified T : ViewModel> viewModelsFactory(crossinline viewModelInitialization: () -> T): Lazy<T> {
        return viewModels {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return viewModelInitialization.invoke() as T
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Timber.e("SAVING STATE")
    }
}

