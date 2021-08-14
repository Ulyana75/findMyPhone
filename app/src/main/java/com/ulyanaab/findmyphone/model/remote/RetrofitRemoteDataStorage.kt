package com.ulyanaab.findmyphone.model.remote

import com.ulyanaab.findmyphone.App
import com.ulyanaab.findmyphone.model.objects.MetricsList
import com.ulyanaab.findmyphone.model.objects.PhoneMetrics
import com.ulyanaab.findmyphone.model.objects.UserModel
import com.ulyanaab.findmyphone.model.objects.UserResponse
import com.ulyanaab.findmyphone.utilities.token
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RetrofitRemoteDataStorage : RemoteDataStorage {

    private val retrofitApi = App.retrofitApi

    override fun sendMetrics(data: List<PhoneMetrics>, callback: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            retrofitApi.pushMetrics(
                token,
                MetricsList(
                    data = data
                )
            )
            callback()
        }
    }

    override fun sendUser(user: UserModel, callback: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = retrofitApi.pushNewUser(
                user.deviceId
            )
            token = response.token
            callback()
        }
    }

}