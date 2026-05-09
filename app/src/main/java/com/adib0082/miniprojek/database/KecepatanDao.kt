package com.adib0082.miniprojek.database

import androidx.room.*
import com.adib0082.miniprojek.model.Kategori
import com.adib0082.miniprojek.model.SensorKecepatan
import kotlinx.coroutines.flow.Flow

@Dao
interface KecepatanDao {
    // Hanya ambil data yang belum dihapus
    @Query("SELECT * FROM sensor_kecepatan WHERE isDeleted = 0 ORDER BY waktu DESC")
    fun getAllActiveData(): Flow<List<SensorKecepatan>>

    // Ambil data dari Recycle Bin
    @Query("SELECT * FROM sensor_kecepatan WHERE isDeleted = 1 ORDER BY waktu DESC")
    fun getDeletedData(): Flow<List<SensorKecepatan>>

    @Insert
    suspend fun insert(data: SensorKecepatan)

    @Update
    suspend fun update(data: SensorKecepatan)

    @Query("SELECT * FROM sensor_kecepatan WHERE id = :id")
    suspend fun getDataById(id: Long): SensorKecepatan?

    // Soft Delete (Pindahkan ke Recycle Bin)
    @Query("UPDATE sensor_kecepatan SET isDeleted = 1 WHERE id = :id")
    suspend fun softDelete(id: Long)

    // Restore (Undo hapus)
    @Query("UPDATE sensor_kecepatan SET isDeleted = 0 WHERE id = :id")
    suspend fun restore(id: Long)

    // Hapus Permanen
    @Query("DELETE FROM sensor_kecepatan WHERE id = :id")
    suspend fun deletePermanently(id: Long)

    // --- Tabel Kategori ---
    @Insert
    suspend fun insertKategori(kategori: Kategori)

    @Query("SELECT * FROM kategori")
    fun getAllKategori(): Flow<List<Kategori>>
}
