package com.ulyanaab.findmyphone.view

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ulyanaab.findmyphone.R
import com.ulyanaab.findmyphone.utilities.APP_ACTIVITY
import com.ulyanaab.findmyphone.utilities.REQUEST_LOCATION_CODE
import com.ulyanaab.findmyphone.utilities.replaceFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        APP_ACTIVITY = this
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        replaceFragment(GetPermissionsFragment())
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
        }
    }

}