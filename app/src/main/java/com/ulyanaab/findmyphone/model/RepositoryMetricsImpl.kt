package com.ulyanaab.findmyphone.model

import com.ulyanaab.findmyphone.model.local.LocalDataStorage
import com.ulyanaab.findmyphone.model.local.RoomLocalDataStorage
import com.ulyanaab.findmyphone.model.objects.MetricsList
import com.ulyanaab.findmyphone.model.objects.PhoneMetrics
import com.ulyanaab.findmyphone.model.remote.RemoteDataStorage
import com.ulyanaab.findmyphone.model.remote.RetrofitRemoteDataStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*

class RepositoryMetricsImpl : RepositoryMetrics {

    private val localDataStorage: LocalDataStorage = RoomLocalDataStorage()
    private val remoteDataStorage: RemoteDataStorage = RetrofitRemoteDataStorage()


    override fun sendData(data: List<PhoneMetrics>, callback: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            sendToRemoteStorage(localDataStorage.getAll() + data,
                onSuccess = {
                    callback()
                    localDataStorage.deleteAll()
                },
                onFailure = {
                    sendToLocalStorage(data, callback)
                })
        }
    }

    override fun getLast(token: String): PhoneMetrics = runBlocking {
        return@runBlocking remoteDataStorage.getLast(token)
    }

    override fun getAll(token: String): MetricsList = runBlocking {
        return@runBlocking remoteDataStorage.getAll(token)
    }

    override fun getByTime(token: String, timeBegin: String, timeEnd: String): MetricsList =
        runBlocking {
            return@runBlocking remoteDataStorage.getByTime(token, timeBegin, timeEnd)
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