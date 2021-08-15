package com.ulyanaab.findmyphone.view.childPart

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.ulyanaab.findmyphone.utilities.LOW_BATTERY_TIME_DELAY
import com.ulyanaab.findmyphone.utilities.OKAY_BATTERY_TIME_DELAY
import com.ulyanaab.findmyphone.utilities.liveDataTimeDelay

class BatteryReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BATTERY_LOW) {
            liveDataTimeDelay.value = LOW_BATTERY_TIME_DELAY
        } else if (intent.action == Intent.ACTION_BATTERY_OKAY) {
            liveDataTimeDelay.value = OKAY_BATTERY_TIME_DELAY
        }
    }
}