package com.ulyanaab.findmyphone.model.objects

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PhoneMetrics")
data class PhoneMetrics(
    val token: String,
    val cellId: Int?,
    val latitude: Double?,
    val longitude: Double?,
    val lac: Int?,
    val rsrp: Int?,
    val rsrq: Int?,
    val sinr: Int?,
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
)
