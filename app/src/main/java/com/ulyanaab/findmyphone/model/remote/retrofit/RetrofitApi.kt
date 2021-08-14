package com.ulyanaab.findmyphone.model.remote.retrofit

import com.ulyanaab.findmyphone.model.objects.MetricsList
import com.ulyanaab.findmyphone.model.objects.PhoneMetrics
import com.ulyanaab.findmyphone.model.objects.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

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

}