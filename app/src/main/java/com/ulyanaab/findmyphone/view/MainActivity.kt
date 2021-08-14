package com.ulyanaab.findmyphone.view

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ulyanaab.findmyphone.R
import com.ulyanaab.findmyphone.utilities.*
import com.ulyanaab.findmyphone.view.childPart.GetPermissionsFragment
import com.ulyanaab.findmyphone.view.childPart.InitUserFragment
import com.ulyanaab.findmyphone.view.childPart.StartChildFragment
import com.ulyanaab.findmyphone.view.parentPart.MainParentFragment
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        APP_ACTIVITY = this
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        checkAuth()
    }

    private fun checkAuth() {
        val sPref = getSharedPreferences(TOKEN_PREFERENCE, MODE_PRIVATE)
        val uid = sPref.getString(TOKEN_KEY, "null")
        if (uid == "null") {
            replaceFragment(ChooseAccountFragment())
        } else if (uid == PARENT_TOKEN) {
            replaceFragment(MainParentFragment())
        } else {
            replaceFragment(StartChildFragment())
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION_CODE) {
            grantResults.forEach {
                if (it == PackageManager.PERMISSION_GRANTED) {
                    replaceFragment(InitUserFragment())
                    return
                }
            }
            replaceFragment(GetPermissionsFragment())
        }
    }

}