package com.ulyanaab.findmyphone.model.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ulyanaab.findmyphone.model.PhoneMetrics
import com.ulyanaab.findmyphone.utilities.DATABASE_NAME

@Database(entities = [PhoneMetrics::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun phoneMetricsDao(): MetricsDao

    companion object {
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                val _instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).build()
                instance = _instance
                _instance
            }
        }
    }

}