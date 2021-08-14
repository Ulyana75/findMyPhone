package com.ulyanaab.findmyphone.model

import com.ulyanaab.findmyphone.model.local.LocalDataStorage
import com.ulyanaab.findmyphone.model.local.RoomLocalDataStorage
import com.ulyanaab.findmyphone.model.objects.PhoneMetrics
import com.ulyanaab.findmyphone.model.remote.RemoteDataStorage
import com.ulyanaab.findmyphone.model.remote.RetrofitRemoteDataStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class RepositoryMetricsImpl : RepositoryMetrics {

    private val localDataStorage: LocalDataStorage = RoomLocalDataStorage()
    private val remoteDataStorage: RemoteDataStorage = RetrofitRemoteDataStorage()


    override fun sendData(data: List<PhoneMetrics>, callback: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            sendToRemoteStorage(localDataStorage.getAll() + data,
                onSuccess = callback,
                onFailure = {
                    sendToLocalStorage(data, callback)
                })
            localDataStorage.deleteAll()
        }
    }

    private fun sendToLocalStorage(data: List<PhoneMetrics>, callback: () -> Unit) {
        localDataStorage.sendData(data, callback)
    }

    private fun sendToRemoteStorage(
        data: List<PhoneMetrics>,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        remoteDataStorage.sendMetrics(data, onSuccess, onFailure)
    }
}