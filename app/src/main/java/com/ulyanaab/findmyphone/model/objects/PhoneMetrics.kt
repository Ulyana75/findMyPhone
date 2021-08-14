package com.ulyanaab.findmyphone.model.objects

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.*

@Entity(tableName = "PhoneMetrics")
data class PhoneMetrics(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val fromToken: String,
    val date: String,
    val latitude: Double?,
    val longitude: Double?,
    val cellId: Int?,
    val lac: Int?,
    val rsrp: Int?,
    val rsrq: Int?,
    val sinr: Int?,
)
