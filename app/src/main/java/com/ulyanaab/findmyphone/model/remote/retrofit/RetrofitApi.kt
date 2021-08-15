package com.ulyanaab.findmyphone.model.remote.retrofit

import com.ulyanaab.findmyphone.model.objects.PhoneMetrics
import retrofit2.http.*

interface RetrofitApi {

    @POST("push/new_child")
    suspend fun pushNewUser(
        @Body deviceId: String
    ): String

    @POST("push/metrics/one")
    fun pushOneMetric(
        @Body phoneMetrics: PhoneMetrics
    )

    @POST("push/metrics/many")
    suspend fun pushMetrics(
        @Body listMetrics: List<PhoneMetrics>
    )

    @GET("get/{token}/last_record")
    suspend fun getLastRecord(@Path("token") token: String): PhoneMetrics

    @GET("get/{token}/all_records")
    suspend fun getAllRecords(@Path("token") token: String): List<PhoneMetrics>

    @GET("get/{token}/records")
    suspend fun getRecordsByTime(
        @Path("token") token: String,
        @Query("time_begin") timeBegin: String,
        @Query("time_end") timeEnd: String
    ): List<PhoneMetrics>

}