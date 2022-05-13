package com.example.forecaster.data

import android.content.Context
import androidx.room.Room
import com.example.forecaster.R
import com.example.forecaster.domain.Mapper.toDto
import com.example.forecaster.domain.Mapper.toModel
import com.example.forecaster.data.dao.WeatherDao
import com.example.forecaster.domain.model.City
import com.example.forecaster.domain.model.WeatherWrapper
import com.example.forecaster.data.retrofit.RetrofitService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class MainRepository private constructor(private val service: RetrofitService, private val dao: WeatherDao) {
    suspend fun getWeatherFromNet(name: String): WeatherWrapper = withContext(Dispatchers.IO) {
        return@withContext try {
            val response = service.getWeather(name)

            if (response.isSuccessful && response.body() != null) {
                dao.getDaysCount()?.let { if (it > 14) dao.nukeOld() }

                dao.insertAll(response.body()!!.list.map { it.toDto() })
                response.body()!!
            } else {
                throw Exception()
            }
        } catch (e: Exception) {
            Timber.e(e)
            WeatherWrapper(listOf(), City(name))
        }
    }

    suspend fun getWeatherFromDb(name: String) = withContext(Dispatchers.IO) {
        return@withContext WeatherWrapper(try {
            val d = dao.getLastWeek().map { it.toModel() }
            d
        } catch (e: Exception) {
            listOf()
        }, City(name))
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
                    ).build().weatherDao()
                )

                repository
            }
        }
    }
}