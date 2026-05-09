package com.adib0082.miniprojek.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "db")
data class KecepatanSensor(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val nilai: Double,      // Nilai kecepatan (misal: 10.5)
    val jenis: String,     // "Angin" atau "Air"
    val lokasi: String,    // Lokasi pengambilan data
    val waktu: Long = System.currentTimeMillis() // Timestamp otomatis
)