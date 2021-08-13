package com.ulyanaab.findmyphone.model

import com.ulyanaab.findmyphone.model.local.LocalDataStorage
import com.ulyanaab.findmyphone.model.local.RoomLocalDataStorage
import com.ulyanaab.findmyphone.model.remote.RemoteDataStorage
import com.ulyanaab.findmyphone.model.remote.RetrofitRemoteDataStorage
import java.lang.Exception

class RepositoryImpl : Repository {

    private val localDataStorage: LocalDataStorage = RoomLocalDataStorage()
    private val remoteDataStorage: RemoteDataStorage = RetrofitRemoteDataStorage()

    override fun sendData(data: List<PhoneMetrics>, callback: () -> Unit) {
        try {
            sendToRemoteStorage(data)
        } catch (_: Exception) {
            sendToLocalStorage(data)
        }
    }

    private fun sendToLocalStorage(data: List<PhoneMetrics>) {
        localDataStorage.sendData(data)
    }

    private fun sendToRemoteStorage(data: List<PhoneMetrics>) {
        remoteDataStorage.sendData(data)
    }
}