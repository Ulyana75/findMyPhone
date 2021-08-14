package com.ulyanaab.findmyphone.controllers

import android.annotation.SuppressLint
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import com.ulyanaab.findmyphone.model.RepositoryUser
import com.ulyanaab.findmyphone.model.RepositoryUserImpl
import com.ulyanaab.findmyphone.model.objects.UserModel
import com.ulyanaab.findmyphone.utilities.APP_ACTIVITY
import com.ulyanaab.findmyphone.utilities.TOKEN_KEY
import com.ulyanaab.findmyphone.utilities.TOKEN_PREFERENCE
import com.ulyanaab.findmyphone.utilities.token

class InitUserController {

    private val repository: RepositoryUser = RepositoryUserImpl()


    fun isInited(): Boolean {
        val sPref = APP_ACTIVITY.getSharedPreferences(
            TOKEN_PREFERENCE,
            AppCompatActivity.MODE_PRIVATE
        )

        val uid = sPref.getString(TOKEN_KEY, "null")

        if (uid == "null") {
            return false
        } else {
            token = uid!!
            return true
        }
    }

    @SuppressLint("HardwareIds")
    fun sendUser(onSuccess: () -> Unit, onFailure: () -> Unit) {
        val user = UserModel(
            deviceId = Settings.Secure.getString(
                APP_ACTIVITY.applicationContext.contentResolver,
                Settings.Secure.ANDROID_ID
            )
        )

        repository.sendData(
            user,
            onSuccess = {
                APP_ACTIVITY.getSharedPreferences(
                    TOKEN_PREFERENCE,
                    AppCompatActivity.MODE_PRIVATE
                ).edit().putString(TOKEN_KEY, token).apply()

                onSuccess()
            },
            onFailure = onFailure
        )
    }

}