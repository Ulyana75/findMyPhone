package com.ulyanaab.findmyphone.model.remote

import android.util.Log
import com.ulyanaab.findmyphone.App
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
                    data
                )
                onSuccess()
            } catch (e: Exception) {
                e.printStackTrace()
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
                token = response
                Log.d("OkHttp", "token $token")
                withContext(Dispatchers.Main) {
                    onSuccess()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    onFailure()
                }
            }
        }
    }

    override fun getLast(token: String): PhoneMetrics = runBlocking {
        return@runBlocking retrofitApi.getLastRecord(token)
    }

    override fun getAll(token: String): List<PhoneMetrics> = runBlocking {
        return@runBlocking retrofitApi.getAllRecords(token)
    }

    override fun getByTime(token: String, timeBegin: String, timeEnd: String): List<PhoneMetrics> =
        runBlocking {
            return@runBlocking retrofitApi.getRecordsByTime(token, timeBegin, timeEnd)
        }

}