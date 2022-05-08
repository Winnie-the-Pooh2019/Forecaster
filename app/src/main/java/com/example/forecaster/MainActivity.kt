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

        fragment = (if (savedInstanceState == null)
            RetainFragment().apply {
                supportFragmentManager
                    .beginTransaction()
                    .add(this, FRAGMENT_TAG)
                    .commit()
            }
        else
            supportFragmentManager.getFragment(savedInstanceState, FRAGMENT_TAG) as RetainFragment)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Timber.e("SAVING STATE")

        supportFragmentManager.saveFragmentInstanceState(fragment)
        supportFragmentManager.putFragment(outState, FRAGMENT_TAG, fragment)
    }

    companion object {
        const val FRAGMENT_TAG = "fragment"
    }
}

