package com.ulyanaab.findmyphone.model.local

import com.ulyanaab.findmyphone.model.PhoneMetrics

interface LocalDataStorage {

    fun sendData(data: List<PhoneMetrics>)

}