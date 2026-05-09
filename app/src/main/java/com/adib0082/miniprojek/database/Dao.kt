package com.adib0082.miniprojek.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.adib0082.miniprojek.model.Mahasiswa
import kotlinx.coroutines.flow.Flow

@Dao
interface MahasiswaDao {
    @Insert
    suspend fun insert(mahasiswa: Mahasiswa)
    @Update
    suspend fun update(mahasiswa: Mahasiswa)
    @Query("SELECT * FROM mahasiswa ORDER BY nama DESC")
    fun getMahasiswa(): Flow<List<Mahasiswa>>
    @Query("SELECT * FROM mahasiswa WHERE id = :id")
    suspend fun getMahasiswaById(id: Long): Mahasiswa?
    @Query("DELETE FROM mahasiswa WHERE id = :id")
    suspend fun delete(id: Long)
}