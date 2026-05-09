package com.adib0082.miniprojek.database

import androidx.room.*
import com.adib0082.miniprojek.model.SensorKecepatan
import kotlinx.coroutines.flow.Flow

@Dao
interface KecepatanDao {
    @Insert
    suspend fun insert(data: SensorKecepatan)

    @Update
    suspend fun update(data: SensorKecepatan)

    @Query("SELECT * FROM kecepatan_sensor ORDER BY waktu DESC")
    fun getAllData(): Flow<List<SensorKecepatan>>

    @Query("SELECT * FROM kecepatan_sensor WHERE id = :id")
    suspend fun getDataById(id: Long): SensorKecepatan?

    @Query("DELETE FROM kecepatan_sensor WHERE id = :id")
    suspend fun deleteById(id: Long)
}