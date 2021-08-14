package com.ulyanaab.findmyphone.controllers

import com.ulyanaab.findmyphone.model.RepositoryMetrics
import com.ulyanaab.findmyphone.model.RepositoryMetricsImpl
import com.ulyanaab.findmyphone.model.objects.PhoneMetrics

class ServiceController {

    private val repository: RepositoryMetrics = RepositoryMetricsImpl()


    fun sendData(data: List<PhoneMetrics>, callback: () -> Unit) {
        repository.sendData(data, callback)
    }

}