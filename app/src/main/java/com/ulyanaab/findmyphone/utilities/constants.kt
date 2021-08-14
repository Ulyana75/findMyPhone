package com.ulyanaab.findmyphone.utilities

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.ulyanaab.findmyphone.view.MainActivity
import java.text.SimpleDateFormat

lateinit var APP_ACTIVITY: MainActivity

const val REQUEST_LOCATION_CODE = 1

const val DATABASE_NAME = "app_database"

lateinit var token: String

const val TOKEN_PREFERENCE = "TokenSPref"

const val TOKEN_KEY = "token"

const val BASE_URL = "https://www.chess.com"

val liveDataNeedToStop = MutableLiveData<Boolean>()

const val KEY_TO_SEND_TOKEN = "token"

const val PARENT_TOKEN = "parent"

@SuppressLint("SimpleDateFormat")
val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")