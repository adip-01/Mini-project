package com.adib0082.miniprojek.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adib0082.miniprojek.database.KecepatanDao
import com.adib0082.miniprojek.model.SensorKecepatan
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(private val dao: KecepatanDao) : ViewModel() {
    // Hanya menampilkan data yang aktif (isDeleted = false)
    val data: StateFlow<List<SensorKecepatan>> = dao.getAllActiveData().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // Data untuk Recycle Bin
    val deletedData: StateFlow<List<SensorKecepatan>> = dao.getDeletedData().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun restore(id: Long) {
        viewModelScope.launch {
            dao.restore(id)
        }
    }

    fun deletePermanently(id: Long) {
        viewModelScope.launch {
            dao.deletePermanently(id)
        }
    }
}
