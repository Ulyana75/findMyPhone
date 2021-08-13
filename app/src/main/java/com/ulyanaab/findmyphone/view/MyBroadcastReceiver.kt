package com.ulyanaab.findmyphone.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat

class MyBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        context.let {
            ContextCompat.startForegroundService(it, Intent(it, LocationService::class.java))
        }
    }
}