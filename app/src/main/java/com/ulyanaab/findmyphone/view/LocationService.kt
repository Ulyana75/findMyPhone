package com.ulyanaab.findmyphone.view

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
import android.provider.Settings
import android.telephony.CellInfoLte
import android.telephony.TelephonyManager
import android.telephony.gsm.GsmCellLocation
import android.util.Log
import androidx.core.app.NotificationCompat
import com.ulyanaab.findmyphone.R
import com.ulyanaab.findmyphone.model.PhoneMetrics
import com.ulyanaab.findmyphone.model.Repository
import com.ulyanaab.findmyphone.model.RepositoryImpl

@SuppressLint("MissingPermission")
class LocationService : Service() {

    private val NOTIFICATION_CHANNEL_ID = "notification_channel"
    private val TIME_DELAY_SAVE_METRICS: Long = 1000
    private val MAX_BUFFER_SIZE = 10
    private val MIN_DIST = 0f

    private lateinit var telephonyManager: TelephonyManager
    private lateinit var locationManager: LocationManager
    private lateinit var locationListener: MyLocationListener

    private val buffer = mutableListOf<PhoneMetrics>()

    private val repository: Repository = RepositoryImpl()

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
        super.onCreate()
        makeNotification()
        initManagers()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        isServiceStarted = true
        handler.post(runnable)
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        isServiceStarted = false
        handler.removeCallbacks(runnable)
        locationManager.removeUpdates(locationListener)
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
    }


    private fun saveMetricsToBuffer() {
        buffer.add(getMetrics())
        if (buffer.size >= MAX_BUFFER_SIZE) {
            sendData {
                buffer.clear()
            }
        }
    }

    @SuppressLint("HardwareIds")
    private fun getMetrics(): PhoneMetrics {
        val location = telephonyManager.cellLocation as GsmCellLocation
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
            cellId = location.cid,
            deviceId = Settings.Secure.getString(
                applicationContext.contentResolver,
                Settings.Secure.ANDROID_ID
            ),
            latitude = locationListener.getLatitude(),
            longitude = locationListener.getLongitude(),
            lac = location.lac,
            rsrp = rsrp,
            rsrq = rsrq,
            sinr = sinr,
            imsi = "0"   // telephonyManager.subscriberId
        )
        //Log.d("LOL", res.toString())
        return res
    }

    private fun sendData(callback: () -> Unit) {
        //repository.sendData(buffer, callback)
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

        private var curLocation: Location? = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

        override fun onLocationChanged(location: Location) {
            curLocation = location
        }

        fun getLatitude(): Double? {
            return curLocation?.latitude
        }

        fun getLongitude(): Double? {
            return curLocation?.longitude
        }

    }
}