package com.ulyanaab.findmyphone.model.remote

import com.ulyanaab.findmyphone.model.objects.PhoneMetrics
import com.ulyanaab.findmyphone.model.objects.UserModel

interface RemoteDataStorage {

    fun sendMetrics(data: List<PhoneMetrics>, callback: () -> Unit)

    fun sendUser(user: UserModel, callback: () -> Unit)

}