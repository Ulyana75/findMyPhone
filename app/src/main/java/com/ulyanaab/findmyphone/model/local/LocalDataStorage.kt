package com.ulyanaab.findmyphone.model.local

import com.ulyanaab.findmyphone.model.objects.PhoneMetrics

interface LocalDataStorage {

    fun sendData(data: List<PhoneMetrics>, callback: () -> Unit)

    fun getAll(): List<PhoneMetrics>

    fun deleteAll()

}