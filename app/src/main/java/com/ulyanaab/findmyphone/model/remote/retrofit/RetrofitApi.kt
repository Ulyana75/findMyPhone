package com.ulyanaab.findmyphone.model.remote.retrofit

import com.ulyanaab.findmyphone.model.objects.MetricsList
import com.ulyanaab.findmyphone.model.objects.PhoneMetrics
import com.ulyanaab.findmyphone.model.objects.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface RetrofitApi {

    @POST("push/new_child")
    suspend fun pushNewUser(
        @Body deviceId: String
    ): UserResponse

    @POST("push/metrics/one")
    fun pushOneMetric(
        @Body phoneMetrics: PhoneMetrics
    )

    @POST("push/metrics/many")
    fun pushMetrics(
        @Body listMetrics: MetricsList
    )

    @GET("get/{token}/last_record")
    fun getLastRecord(@Path("token") token: String): PhoneMetrics

    @GET("get/{token}/all_records")
    fun getAllRecords(@Path("token") token: String): MetricsList

    @GET("get/{token}/records")
    fun getRecordsByTime(
        @Path("token") token: String,
        @Query("time_begin") timeBegin: Long,
        @Query("time_end") timeEnd: Long
    ): MetricsList

}