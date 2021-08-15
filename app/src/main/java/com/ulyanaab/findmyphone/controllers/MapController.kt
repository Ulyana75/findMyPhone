package com.ulyanaab.findmyphone.controllers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ulyanaab.findmyphone.model.RepositoryMetrics
import com.ulyanaab.findmyphone.model.RepositoryMetricsImpl
import com.ulyanaab.findmyphone.model.objects.PhoneMetrics
import com.ulyanaab.findmyphone.utilities.dateFormat
import kotlinx.coroutines.*
import java.lang.Exception
import java.util.*

class MapController {

    val pointsLiveData: LiveData<List<PhoneMetrics>> get() = _pointsLiveData
    private val _pointsLiveData = MutableLiveData<List<PhoneMetrics>>()

    private val repositoryMetrics: RepositoryMetrics = RepositoryMetricsImpl()

    fun getData(token: String, timeBegin: Date, timeEnd: Date, onFailure: () -> Unit) =
        CoroutineScope(Dispatchers.IO).launch {
            try {
                _pointsLiveData.postValue(
                    repositoryMetrics.getByTime(
                        token,
                        dateFormat.format(timeBegin),
                        dateFormat.format(timeEnd)
                    )
                )
            } catch (_: Exception) {
                withContext(Dispatchers.Main) {
                    onFailure()
                }
            }
        }

}