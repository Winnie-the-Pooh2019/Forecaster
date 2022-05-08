package com.example.forecaster

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
import java.io.File
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var weatherList: List<ListItem>
    private lateinit var fragment: RetainFragment
    private val TASK_FRAGMENT_TAG = "fragment"

    private val service: RetrofitServices by lazy {
        Common.retrofitService
    }

    override fun onSaveInstanceState(outState: Bundle) {
        val bundle = Bundle().apply {
//            putParcelableArray("array", weatherList)
        }

        super.onSaveInstanceState(outState)
    }

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

//        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction()
//                .add(RetainFragment.newInstance(), null)
//                .commit()
//        }
        fragment = supportFragmentManager.findFragmentByTag(TASK_FRAGMENT_TAG) as RetainFragment? ?:
                // Otherwise, we create a new one and add it to the fragment manager.
                RetainFragment().apply {
                    // We need to pass in the number we want to add to `5`.
                    val arguments = Bundle()
                    arguments.putInt(RetainFragment.NUMBER_TO_ADD, 10)
                    supportFragmentManager
                        .beginTransaction()
                        .add(this, TASK_FRAGMENT_TAG)
                        .commit()
                }

        val weatherAdapter = WeatherAdapter()
        findViewById<RecyclerView>(R.id.recycler_vew).apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = weatherAdapter
        }

        getForecast(getString(R.string.city), weatherAdapter)
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.e("ACTIVITY DESTROYED")
    }

    private fun getForecast(city: String, adapter: WeatherAdapter) {
        service.getWeather(city).enqueue(object : Callback<Wrapper> {
            override fun onResponse(call: Call<Wrapper>, response: Response<Wrapper>) {
                try {
                    val data = response.body() as Wrapper
                    weatherList = data.list
                    Timber.tag("LOGGING DATA").w(data.toString())

                    adapter.submitList(weatherList)
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