package com.ulyanaab.findmyphone.model

import com.ulyanaab.findmyphone.model.objects.MetricsList
import com.ulyanaab.findmyphone.model.objects.PhoneMetrics

interface RepositoryMetrics {

    fun sendData(data: List<PhoneMetrics>, callback: () -> Unit)

    fun getLast(token: String): PhoneMetrics

    fun getAll(token: String): MetricsList

    fun getByTime(token: String, timeBegin: Long, timeEnd: Long): MetricsList

}