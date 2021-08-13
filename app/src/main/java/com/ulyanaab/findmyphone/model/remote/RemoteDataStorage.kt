package com.ulyanaab.findmyphone.model.remote

import com.ulyanaab.findmyphone.model.PhoneMetrics

interface RemoteDataStorage {

    fun sendData(data: List<PhoneMetrics>)

}