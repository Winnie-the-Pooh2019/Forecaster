package com.example.forecaster.data

import android.content.Context
import androidx.room.Room
import com.example.forecaster.R
import com.example.forecaster.data.Mapper.toDto
import com.example.forecaster.data.Mapper.toModel
import com.example.forecaster.data.dao.WeatherDao
import com.example.forecaster.data.model.WeatherWrapper
import com.example.forecaster.data.retrofit.RetrofitService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainRepository private constructor(private val service: RetrofitService, private val dao: WeatherDao) {
    suspend fun getWeather(name: String): WeatherWrapper = withContext(Dispatchers.IO) {
        val response = service.getWeather(name)

        return@withContext try {
            if (response.isSuccessful && response.body() != null) {
                if (dao.count() > 100)
                    dao.nuke()

                dao.insertAll(response.body()!!.list.map { it.toDto() })
                response.body()!!
            } else
                WeatherWrapper(dao.getAll().map { it.toModel() })
        } catch (e: Exception) {
            WeatherWrapper(listOf())
        }
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
                        WeatherDatabase::class.java, context.getString(R.string.db_name)
                    ).build().weatherDao())

                repository
            }
        }
    }
}