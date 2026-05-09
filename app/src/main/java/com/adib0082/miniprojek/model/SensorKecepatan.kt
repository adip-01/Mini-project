package com.adib0082.miniprojek.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "kecepatan_sensor")
data class SensorKecepatan(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val nilai: Double,
    val jenis: String,
    val lokasi: String,
    val waktu: Long = System.currentTimeMillis(),
    val isDeleted: Boolean = false, // Fitur Recycle Bin
    val kategoriId: Long = 0L       // Fitur Multi-table
)
