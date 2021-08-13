package com.ulyanaab.findmyphone.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.ulyanaab.findmyphone.App
import com.ulyanaab.findmyphone.R
import com.ulyanaab.findmyphone.utilities.APP_ACTIVITY
import com.ulyanaab.findmyphone.utilities.REQUEST_LOCATION_CODE
import com.ulyanaab.findmyphone.utilities.replaceFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        APP_ACTIVITY = this
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        requestLocationPermissions()
        CoroutineScope(Dispatchers.IO).launch {
            Log.d("LOL", App.database.phoneMetricsDao().getAll().toString())
        }
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