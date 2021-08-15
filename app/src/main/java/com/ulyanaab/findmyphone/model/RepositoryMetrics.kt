package com.ulyanaab.findmyphone.model

import com.ulyanaab.findmyphone.model.objects.PhoneMetrics

interface RepositoryMetrics {

    fun sendData(data: List<PhoneMetrics>, callback: () -> Unit)

    fun getLast(token: String): PhoneMetrics

    fun getAll(token: String): List<PhoneMetrics>

    fun getByTime(token: String, timeBegin: String, timeEnd: String): List<PhoneMetrics>

}