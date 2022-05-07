package com.example.forecaster

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.forecaster.adapter.WeatherAdapter
import com.example.forecaster.common.Common
import com.example.forecaster.model.Wrapper
import com.example.forecaster.retrofit.RetrofitServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.io.File
import java.io.IOException

var retainFragment: RetainFragment = RetainFragment()
var TAG = "fragment"

class MainActivity : AppCompatActivity() {
    private lateinit var fragment: RetainFragment
    
    private val service: RetrofitServices by lazy {
        Common.retrofitService
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

//        fragment = supportFragmentManager.findFragmentByTag(RetainFragment.TAG) as RetainFragment? ?:
//                // Otherwise, we create a new one and add it to the fragment manager.
//                RetainFragment().apply {
//                    // We need to pass in the number we want to add to `5`.
//                    supportFragmentManager
//                        .beginTransaction()
//                        .add(this, RetainFragment.TAG)
//                        .commit()
//                }
//
//        val weatherAdapter = WeatherAdapter()
//        findViewById<RecyclerView>(R.id.recycler_vew).apply {
//            layoutManager = LinearLayoutManager(this@MainActivity)
//            adapter = weatherAdapter
//        }
//
//        getForecast(getString(R.string.city), weatherAdapter)

        if(savedInstanceState != null){

            (supportFragmentManager.getFragment(
                savedInstanceState,
                TAG
            ) as RetainFragment).also { retainFragment = it }
            retainFragment.getSavedData()

        }else{
            retainFragment = RetainFragment().apply {
                supportFragmentManager
                    .beginTransaction()
                    .add(this, TAG)
                    .commit()
            }
            retainFragment.getAllWeatherList("Shklov")
        }
    }

    override fun onSaveInstanceState(outState: Bundle){
        supportFragmentManager.putFragment(outState,TAG, retainFragment)
        supportFragmentManager.saveFragmentInstanceState(retainFragment)
        super.onSaveInstanceState(outState)
    }

    private fun getForecast(city: String, adapter: WeatherAdapter) {
        service.getWeather(city).enqueue(object : Callback<Wrapper> {
            override fun onResponse(call: Call<Wrapper>, response: Response<Wrapper>) {
                try {
                    val data = response.body() as Wrapper
                    val listWeather = data.list
                    println("Logging list $listWeather")
                    Timber.tag("LOGGING DATA").w(data.toString())
                    println("LOGGING DATA = $data")

                    adapter.submitList(listWeather)
                } catch (e: IOException) {
                    Timber.e("DATA COLLECTED BUT SMTH GOT WRONG")
                    Timber.e(e)
                    println("DATA COLLECTED BUT SMTH GOT WRONG")
                }
            }

            override fun onFailure(call: Call<Wrapper>, t: Throwable) {
                Timber.e("CANNOT ESTABLISH CONNECTION TO API")
                println("CANNOT ESTABLISH CONNECTION TO API")
            }
        })
    }
}