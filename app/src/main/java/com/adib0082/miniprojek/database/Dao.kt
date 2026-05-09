package com.adib0082.miniprojek.database

import androidx.room.*
import com.adib0082.miniprojek.model.KecepatanSensor
import kotlinx.coroutines.flow.Flow

@Dao
interface KecepatanDao {
    @Insert
    suspend fun insert(data: KecepatanSensor)

    @Query("SELECT * FROM kecepatan_sensor ORDER BY waktu DESC")
    fun getAllData(): Flow<List<KecepatanSensor>>

    @Query("DELETE FROM kecepatan_sensor WHERE id = :id")
    suspend fun deleteById(id: Long)
}
