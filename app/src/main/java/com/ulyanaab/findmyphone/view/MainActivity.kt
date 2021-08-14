package com.ulyanaab.findmyphone.view

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ulyanaab.findmyphone.R
import com.ulyanaab.findmyphone.utilities.*
import com.ulyanaab.findmyphone.view.childPart.GetPermissionsFragment
import com.ulyanaab.findmyphone.view.childPart.InitUserFragment

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
        if(uid == "null") {
            replaceFragment(ChooseAccountFragment())
        } else {
            replaceFragment(InitUserFragment())
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
                    replaceFragment(InitUserFragment())
                    return
                }
            }
            replaceFragment(GetPermissionsFragment())
        }
    }

}