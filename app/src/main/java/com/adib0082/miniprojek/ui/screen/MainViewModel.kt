package com.adib0082.miniprojek.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adib0082.miniprojek.database.KecepatanDao
import com.adib0082.miniprojek.model.SensorKecepatan
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class MainViewModel(dao: KecepatanDao) : ViewModel() {
    val data: StateFlow<List<SensorKecepatan>> = dao.getAllData().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
}
