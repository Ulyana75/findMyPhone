package com.ulyanaab.findmyphone.model

import com.ulyanaab.findmyphone.model.local.LocalDataStorage
import com.ulyanaab.findmyphone.model.local.RoomLocalDataStorage
import com.ulyanaab.findmyphone.model.objects.PhoneMetrics
import com.ulyanaab.findmyphone.model.remote.RemoteDataStorage
import com.ulyanaab.findmyphone.model.remote.RetrofitRemoteDataStorage
import java.lang.Exception

class RepositoryMetricsImpl : RepositoryMetrics {

    private val localDataStorage: LocalDataStorage = RoomLocalDataStorage()
    private val remoteDataStorage: RemoteDataStorage = RetrofitRemoteDataStorage()

    override fun sendData(data: List<PhoneMetrics>, callback: () -> Unit) {
        try {
            sendToRemoteStorage(data, callback)
        } catch (_: Exception) {
            sendToLocalStorage(data, callback)
        }
    }

    private fun sendToLocalStorage(data: List<PhoneMetrics>, callback: () -> Unit) {
        localDataStorage.sendData(data, callback)
    }

    private fun sendToRemoteStorage(data: List<PhoneMetrics>, callback: () -> Unit) {
        remoteDataStorage.sendMetrics(data, callback)
    }
}