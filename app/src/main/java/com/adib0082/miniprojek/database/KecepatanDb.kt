package com.adib0082.miniprojek.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.adib0082.miniprojek.model.Kategori
import com.adib0082.miniprojek.model.SensorKecepatan

@Database(entities = [SensorKecepatan::class, Kategori::class], version = 1, exportSchema = false)
abstract class KecepatanDb : RoomDatabase() {
    abstract val dao: KecepatanDao

    companion object {
        @Volatile
        private var INSTANCE: KecepatanDb? = null

        fun getInstance(context: Context): KecepatanDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    KecepatanDb::class.java,
                    "kecepatan_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
