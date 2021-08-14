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

    override fun sendMetrics(data: List<PhoneMetrics>, onSuccess: () -> Unit, onFailure: () -> Unit) {
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
                onSuccess()
            } catch(e: Exception) {
                onFailure()
            }
        }
    }

}