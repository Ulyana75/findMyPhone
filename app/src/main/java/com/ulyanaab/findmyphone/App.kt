package com.ulyanaab.findmyphone

import android.app.Application
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.ulyanaab.findmyphone.model.local.room.AppDatabase
import com.ulyanaab.findmyphone.model.remote.retrofit.RetrofitApi
import com.ulyanaab.findmyphone.utilities.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {

    private lateinit var retrofit: Retrofit

    companion object {
        lateinit var database: AppDatabase
        lateinit var retrofitApi: RetrofitApi
    }

    override fun onCreate() {
        super.onCreate()
        initRetrofit()
        database = AppDatabase.getInstance(this)
    }

    private fun initRetrofit() {
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

        retrofitApi = retrofit.create(RetrofitApi::class.java)
    }

}