package com.ulyanaab.findmyphone.model.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ulyanaab.findmyphone.model.PhoneMetrics

@Dao
interface MetricsDao {

    @Insert
    fun insertAll(data: List<PhoneMetrics>)

    @Query("DELETE FROM PhoneMetrics")
    fun deleteAll()

}