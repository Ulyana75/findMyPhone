package com.ulyanaab.findmyphone.controllers

import com.ulyanaab.findmyphone.model.RepositoryMetrics
import com.ulyanaab.findmyphone.model.RepositoryMetricsImpl
import com.ulyanaab.findmyphone.model.objects.PhoneMetrics

class MapController {

    private val repositoryMetrics: RepositoryMetrics = RepositoryMetricsImpl()

    fun getData(token: String, timeBegin: Long, timeEnd: Long): List<PhoneMetrics> {
        return repositoryMetrics.getByTime(token, timeBegin, timeEnd).data
    }

}