package com.ulyanaab.findmyphone.model.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.TypeConverters
import com.ulyanaab.findmyphone.model.objects.PhoneMetrics

@Dao
@TypeConverters(DateConverter::class)
interface MetricsDao {

    @Insert
    fun insertAll(data: List<PhoneMetrics>)

    @Query("DELETE FROM PhoneMetrics")
    fun deleteAll()

    @Query("SELECT * FROM PhoneMetrics")
    fun getAll(): List<PhoneMetrics>

}