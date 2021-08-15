package com.ulyanaab.findmyphone.model.remote

import com.ulyanaab.findmyphone.App
import com.ulyanaab.findmyphone.model.objects.MetricsList
import com.ulyanaab.findmyphone.model.objects.PhoneMetrics
import com.ulyanaab.findmyphone.model.objects.UserModel
import com.ulyanaab.findmyphone.utilities.token
import kotlinx.coroutines.*
import java.util.*

class RetrofitRemoteDataStorage : RemoteDataStorage {

    private val retrofitApi = App.retrofitApi

    override fun sendMetrics(
        data: List<PhoneMetrics>,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                retrofitApi.pushMetrics(
                    MetricsList(
                        data = data
                    )
                )
                onSuccess()
            } catch (e: Exception) {
                onFailure()
            }
        }
    }

    override fun sendUser(user: UserModel, onSuccess: () -> Unit, onFailure: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = retrofitApi.pushNewUser(
                    user.deviceId
                )
                token = response.token
                withContext(Dispatchers.Main) {
                    onSuccess()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onFailure()
                }
            }
        }
    }

    override fun getLast(token: String): PhoneMetrics = runBlocking {
        return@runBlocking retrofitApi.getLastRecord(token)
    }

    override fun getAll(token: String): MetricsList = runBlocking {
        return@runBlocking retrofitApi.getAllRecords(token)
    }

    override fun getByTime(token: String, timeBegin: String, timeEnd: String): MetricsList =
        runBlocking {
            return@runBlocking retrofitApi.getRecordsByTime(token, timeBegin, timeEnd)
        }

}