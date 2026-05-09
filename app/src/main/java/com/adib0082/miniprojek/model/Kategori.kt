package com.adib0082.miniprojek.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "kategori")
data class Kategori(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val nama: String
)
