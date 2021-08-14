package com.ulyanaab.findmyphone.model

import com.ulyanaab.findmyphone.model.objects.PhoneMetrics

interface RepositoryMetrics {

    fun sendData(data: List<PhoneMetrics>, callback: () -> Unit)

}