package com.example.forecaster.data.dao

import androidx.room.*
import com.example.forecaster.data.dto.WeatherDto

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(users: List<WeatherDto>)

    @Query("SELECT * FROM weather order by date")
    suspend fun getAll(): List<WeatherDto>

    @Query("delete from weather")
    suspend fun nuke()

    @Query("select count(*) from weather")
    suspend fun count(): Int
}