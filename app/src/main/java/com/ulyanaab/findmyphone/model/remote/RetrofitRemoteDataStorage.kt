package com.ulyanaab.findmyphone.model.remote

import com.ulyanaab.findmyphone.model.PhoneMetrics
import java.lang.IllegalArgumentException

class RetrofitRemoteDataStorage : RemoteDataStorage {
    override fun sendData(data: List<PhoneMetrics>, callback: () -> Unit) {
        throw IllegalArgumentException()
    }
}