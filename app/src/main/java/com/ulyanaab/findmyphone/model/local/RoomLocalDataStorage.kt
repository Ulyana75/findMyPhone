package com.ulyanaab.findmyphone.model.local

import android.os.Handler
import android.os.Looper
import com.ulyanaab.findmyphone.App
import com.ulyanaab.findmyphone.model.objects.PhoneMetrics
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RoomLocalDataStorage : LocalDataStorage {

    private val DELETION_TIME_DELAY: Long = 86_400_000   // one day

    private val dao = App.database.phoneMetricsDao()

    private val handler: Handler = Handler(Looper.getMainLooper())

    private val runnable = object : Runnable {
        override fun run() {
            deleteAll()
            handler.postDelayed(this, DELETION_TIME_DELAY)
        }
    }

    init {
        handler.postDelayed(runnable, DELETION_TIME_DELAY)
    }

    override fun sendData(data: List<PhoneMetrics>, callback: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.insertAll(data)
            callback()
        }
    }

    private fun deleteAll() {
        CoroutineScope(Dispatchers.IO).launch {
            dao.deleteAll()
        }
    }

}