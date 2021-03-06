package com.ulyanaab.findmyphone.model.remote

import com.ulyanaab.findmyphone.model.objects.PhoneMetrics
import com.ulyanaab.findmyphone.model.objects.UserModel

interface RemoteDataStorage {

    fun sendMetrics(data: List<PhoneMetrics>, onSuccess: () -> Unit, onFailure: () -> Unit)

    fun sendUser(user: UserModel, onSuccess: () -> Unit, onFailure: () -> Unit)

    fun getLast(token: String): PhoneMetrics

    fun getAll(token: String): List<PhoneMetrics>

    fun getByTime(token: String, timeBegin: String, timeEnd: String): List<PhoneMetrics>

}