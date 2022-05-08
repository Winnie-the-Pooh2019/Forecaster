package com.example.forecaster

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var fragment: RetainFragment

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

        fragment = supportFragmentManager.findFragmentByTag(FRAGMENT_TAG) as RetainFragment? ?:
                RetainFragment().apply {
                    supportFragmentManager
                        .beginTransaction()
                        .add(this, FRAGMENT_TAG)
                        .commit()
                }
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.e("ACTIVITY DESTROYED")
    }

    companion object {
        const val FRAGMENT_TAG = "fragment"
    }
}