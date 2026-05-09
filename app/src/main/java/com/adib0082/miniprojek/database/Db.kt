package com.adib0082.miniprojek.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.adib0082.miniprojek.model.KecepatanSensor

@Database(entities = [KecepatanSensor::class], version = 1, exportSchema = false)
abstract class AppDb : RoomDatabase() {
    abstract val dao: KecepatanDao

    companion object {
        @Volatile
        private var INSTANCE: AppDb? = null
        fun getInstance(context: Context): AppDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDb::class.java,
                    "sensor_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}