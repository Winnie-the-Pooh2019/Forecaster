package com.example.forecaster.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.forecaster.data.dto.WeatherDto

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(users: List<WeatherDto>)

    @Query("SELECT * FROM weather order by date")
    suspend fun getAll(): List<WeatherDto>

    @Query("select * from weather where day in (select day from weather group by day order by day desc limit 7)")
    suspend fun getLastWeek(): List<WeatherDto>

    @Query("select day from weather group by day order by day desc limit 7")
    suspend fun getLastDays(): List<String>

    @Query("select count(*) from weather group by day")
    suspend fun getDaysCount(): Int

    @Query("delete from weather")
    suspend fun nuke()

    @Query("delete from weather where day <> :day")
    suspend fun nukeBesides(day: String)

    @Query("delete from weather where day not in (select day from weather order by day desc limit 7)")
    suspend fun nukeOld()

    @Query("delete from weather where day <> (select max(day) from weather)")
    suspend fun nukeBesidesLast()

    @Query("select count(*) from weather")
    suspend fun count(): Int
}