package com.ulyanaab.findmyphone.view.childPart

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.telephony.CellInfoLte
import android.telephony.TelephonyManager
import android.telephony.gsm.GsmCellLocation
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.ulyanaab.findmyphone.R
import com.ulyanaab.findmyphone.controllers.ServiceController
import com.ulyanaab.findmyphone.model.objects.PhoneMetrics
import com.ulyanaab.findmyphone.utilities.APP_ACTIVITY
import com.ulyanaab.findmyphone.utilities.dateFormat
import com.ulyanaab.findmyphone.utilities.liveDataNeedToStop
import com.ulyanaab.findmyphone.utilities.token
import java.util.*

@SuppressLint("MissingPermission")
class LocationService : Service() {

    private val serviceController = ServiceController()

    private val NOTIFICATION_CHANNEL_ID = "notification_channel"
    private val TIME_DELAY_SAVE_METRICS: Long = 3000
    private val MAX_BUFFER_SIZE = 10
    private val MIN_DIST = 3f

    private lateinit var telephonyManager: TelephonyManager
    private lateinit var locationManager: LocationManager
    private lateinit var locationListener: MyLocationListener

    private val buffer = mutableListOf<PhoneMetrics>()

    private val handler = Handler(Looper.getMainLooper())

    private val runnable = object : Runnable {
        override fun run() {
            saveMetricsToBuffer()
            handler.postDelayed(this, TIME_DELAY_SAVE_METRICS)
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        isServiceStarted = true
        super.onCreate()
        makeNotification()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        initManagers()
        handler.post(runnable)
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        isServiceStarted = false
        handler.removeCallbacks(runnable)
        locationManager.removeUpdates(locationListener)
        Log.d("LOL", "onDestroy")
    }

    private fun initManagers() {
        telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationListener = MyLocationListener()

        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            TIME_DELAY_SAVE_METRICS,
            MIN_DIST,
            locationListener
        )

        liveDataNeedToStop.observe(APP_ACTIVITY) {
            if (it) {
                stopForeground(true)
                stopSelf()
            }
        }
    }

    private fun saveMetricsToBuffer() {
        buffer.add(getMetrics())
        if (buffer.size >= MAX_BUFFER_SIZE) {
            serviceController.sendData(buffer) {
                buffer.clear()
            }
        }
    }

    @SuppressLint("HardwareIds")
    private fun getMetrics(): PhoneMetrics {
        val location = telephonyManager.cellLocation as GsmCellLocation?
        val cellInfo = telephonyManager.allCellInfo
        var rsrp: Int? = null
        var rsrq: Int? = null
        var sinr: Int? = null

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            cellInfo.forEach {
                if (it is CellInfoLte) {
                    rsrp = it.cellSignalStrength.rsrp
                    rsrq = it.cellSignalStrength.rsrq
                    sinr = it.cellSignalStrength.rssnr
                }
            }
        }

        val res = PhoneMetrics(
            fromToken = token,
            date = dateFormat.format(Date()),
            cellId = location?.cid,
            latitude = locationListener.getCurrentLocation()?.latitude,
            longitude = locationListener.getCurrentLocation()?.longitude,
            lac = location?.lac,
            rsrp = rsrp,
            rsrq = rsrq,
            sinr = sinr,
        )
        Log.d("LOL", res.toString())
        return res
    }

    private fun makeNotification() {
        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setOngoing(false)
                .setSmallIcon(R.drawable.ic_egg)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager: NotificationManager =
                getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_ID, NotificationManager.IMPORTANCE_LOW
            )
            notificationChannel.description = NOTIFICATION_CHANNEL_ID
            notificationChannel.setSound(null, null)
            notificationManager.createNotificationChannel(notificationChannel)
            startForeground(1, builder.build())
        }
    }

    companion object {
        var isServiceStarted = false
    }

    inner class MyLocationListener : LocationListener {

        private var curLocation: Location? =
            locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

        override fun onLocationChanged(location: Location) {
            curLocation = location
        }

        override fun onProviderDisabled(provider: String) {
            Toast.makeText(
                this@LocationService,
                getString(R.string.toast_off_gps),
                Toast.LENGTH_LONG
            ).show()
        }

        override fun onProviderEnabled(provider: String) {
        }

        fun getCurrentLocation(): Location? {
            return curLocation
        }

    }
}