package com.ulyanaab.findmyphone

import android.app.Application
import com.ulyanaab.findmyphone.model.local.room.AppDatabase

class App : Application() {

    companion object {
        lateinit var database: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        database = AppDatabase.getInstance(this)
    }

}