package com.ulyanaab.findmyphone.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PhoneMetrics")
data class PhoneMetrics(
    val cellId: Int?, //https://stackoverflow.com/questions/4152373/how-to-know-location-area-code-and-cell-id-in-android-phone
    val deviceId: String, //https://habr.com/ru/sandbox/27109/
    val latitude: Double?,
    val longitude: Double?,
    val lac: Int?, //https://stackoverflow.com/questions/4152373/how-to-know-location-area-code-and-cell-id-in-android-phone
    val rsrp: Int?, //https://coderoad.ru/32531176/%D0%9F%D0%BE%D0%BB%D1%83%D1%87%D0%B8%D1%82%D0%B5-RSRP-%D0%BE%D1%82-CellSignalStrengthLte-%D0%B4%D0%BB%D1%8F-Android-%D0%BF%D1%80%D0%B8%D0%BB%D0%BE%D0%B6%D0%B5%D0%BD%D0%B8%D1%8F-API-17
    //только get rsrp https://developer.android.com/reference/android/telephony/CellSignalStrengthLte
    val rsrq: Int?, //https://developer.android.com/reference/android/telephony/CellSignalStrengthLte
    val sinr: Int?, //https://developer.android.com/reference/android/telephony/CellSignalStrengthLte#getRssnr()
    val imsi: String, //getSubscriberId https://developer.android.com/reference/android/telephony/TelephonyManager
    val userId: String="",
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
)
