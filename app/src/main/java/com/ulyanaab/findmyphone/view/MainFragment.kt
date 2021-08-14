package com.ulyanaab.findmyphone.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.telephony.TelephonyManager
import android.telephony.gsm.GsmCellLocation
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.ulyanaab.findmyphone.R
import com.ulyanaab.findmyphone.model.RepositoryUser
import com.ulyanaab.findmyphone.model.RepositoryUserImpl
import com.ulyanaab.findmyphone.model.objects.UserModel
import com.ulyanaab.findmyphone.utilities.*
import java.util.*


class MainFragment : Fragment() {

    private val repository: RepositoryUser = RepositoryUserImpl()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onStart() {
        super.onStart()
        initUser()
        startService()
    }

    private fun initUser() {
        val sPref = requireContext().getSharedPreferences(
            TOKEN_PREFERENCE,
            AppCompatActivity.MODE_PRIVATE
        )
        val uid = sPref.getString(TOKEN_KEY, "null")
        if (uid == "null") {
            sendUser {
                sPref.edit().putString(TOKEN_KEY, token).apply()
                startService()
            }
        } else {
            token = uid!!
            startService()
        }
    }

    @SuppressLint("HardwareIds")
    private fun sendUser(callback: () -> Unit) {
        val user = UserModel(
            deviceId = Settings.Secure.getString(
                requireContext().applicationContext.contentResolver,
                Settings.Secure.ANDROID_ID
            )
        )
        repository.sendData(user, callback)
    }

    private fun startService() {
        if (!LocationService.isServiceStarted) {
            ContextCompat.startForegroundService(
                requireContext(),
                Intent(requireContext(), LocationService::class.java)
            )
        }
    }

}