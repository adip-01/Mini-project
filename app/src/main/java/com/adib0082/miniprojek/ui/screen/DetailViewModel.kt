package com.adib0082.miniprojek.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adib0082.miniprojek.database.KecepatanDao
import com.adib0082.miniprojek.model.Kategori
import com.adib0082.miniprojek.model.SensorKecepatan
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class DetailViewModel(private val dao: KecepatanDao) : ViewModel() {
    
    val kategoriList: Flow<List<Kategori>> = dao.getAllKategori()

    fun insert(nilai: Double, jenis: String, lokasi: String, kategoriId: Long = 0) {
        val data = SensorKecepatan(
            nilai = nilai,
            jenis = jenis,
            lokasi = lokasi,
            kategoriId = kategoriId
        )
        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(data)
        }
    }

    suspend fun getData(id: Long): SensorKecepatan? {
        return dao.getDataById(id)
    }

    fun update(id: Long, nilai: Double, jenis: String, lokasi: String, kategoriId: Long = 0) {
        val data = SensorKecepatan(
            id = id,
            nilai = nilai,
            jenis = jenis,
            lokasi = lokasi,
            kategoriId = kategoriId
        )
        viewModelScope.launch(Dispatchers.IO) {
            dao.update(data)
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.softDelete(id) // Menggunakan soft delete untuk Recycle Bin
        }
    }
}
