package com.example.forecaster.data

import android.content.Context
import androidx.room.Room
import com.example.forecaster.data.dao.WeatherDao
import com.example.forecaster.data.model.WeatherWrapper
import com.example.forecaster.data.retrofit.RetrofitService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainRepository private constructor(private val service: RetrofitService, private val dao: WeatherDao) {
    suspend fun getWeather(name: String): WeatherWrapper? = withContext(Dispatchers.IO) {
        val response = service.getWeather(name)

        return@withContext if (response.isSuccessful)
            response.body()
        else null
    }

    companion object {
        private lateinit var repository: MainRepository

        fun getInstance(context: Context, service: RetrofitService): MainRepository {
            return if (this::repository.isInitialized)
                return repository
            else {
                repository = MainRepository(
                    service,
                    Room.databaseBuilder(
                        context,
                        WeatherDatabase::class.java, "weatherdb"
                    ).build().weatherDao())

                repository
            }
        }
    }
}