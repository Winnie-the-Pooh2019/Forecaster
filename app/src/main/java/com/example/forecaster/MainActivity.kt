package com.example.forecaster

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.forecaster.common.Common
import com.example.forecaster.retrofit.RetrofitServices
import timber.log.Timber
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var fragment: RetainFragment
    private val TASK_FRAGMENT_TAG = "fragment"

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

        Timber.e("ACTIVITY CREATED")

        fragment = supportFragmentManager.findFragmentByTag(TASK_FRAGMENT_TAG) as RetainFragment? ?:
                // Otherwise, we create a new one and add it to the fragment manager.
                RetainFragment().apply {
                    supportFragmentManager
                        .beginTransaction()
                        .add(this, TASK_FRAGMENT_TAG)
                        .commit()
                }
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.e("ACTIVITY DESTROYED")
    }
}