package com.ulyanaab.findmyphone.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PhoneMetrics")
data class PhoneMetrics(
    val cellId: Int?,
    val deviceId: String,
    val latitude: Double?,
    val longitude: Double?,
    val lac: Int?,
    val rsrp: Int?,
    val rsrq: Int?,
    val sinr: Int?,
    val userId: String,
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
)
