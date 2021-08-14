package com.ulyanaab.findmyphone.model.remote.retrofit

import com.ulyanaab.findmyphone.model.objects.MetricsList
import com.ulyanaab.findmyphone.model.objects.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitApi {

    @POST("push/new_user")
    suspend fun pushNewUser(
        @Query("device_id") deviceId: String?
    ): UserResponse

    @POST("push/metrics/{user_id}")
    fun pushMetrics(
        @Path("user_id") userId: String,
        @Body listMetrics: MetricsList
    )

}