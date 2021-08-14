package com.ulyanaab.findmyphone.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.TelephonyManager
import android.telephony.gsm.GsmCellLocation
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.ulyanaab.findmyphone.R
import com.ulyanaab.findmyphone.model.RepositoryMetrics
import com.ulyanaab.findmyphone.model.RepositoryMetricsImpl
import com.ulyanaab.findmyphone.model.RepositoryUser
import com.ulyanaab.findmyphone.model.RepositoryUserImpl
import com.ulyanaab.findmyphone.utilities.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        APP_ACTIVITY = this
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        requestLocationPermissions()
    }

    private fun requestLocationPermissions() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ), REQUEST_LOCATION_CODE
            )
        } else {
            replaceFragment(MainFragment())
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == REQUEST_LOCATION_CODE) {
            grantResults.forEach {
                if (it == PackageManager.PERMISSION_GRANTED) {
                    replaceFragment(MainFragment())
                    return
                }
            }
            replaceFragment(GetPermissionsFragment())
        }
    }

}