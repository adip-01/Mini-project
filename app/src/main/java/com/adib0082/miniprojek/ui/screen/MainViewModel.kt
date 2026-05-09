package com.adib0082.mobpro1.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adib0082.mobpro1.database.MahasiswaDao
import com.adib0082.mobpro1.model.Mahasiswa
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class MainViewModel(dao: MahasiswaDao) : ViewModel() {
    val data: StateFlow<List<Mahasiswa>> = dao.getMahasiswa().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )
}