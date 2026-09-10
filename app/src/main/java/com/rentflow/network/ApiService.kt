package com.rentflow.network

import com.rentflow.model.*
import com.rentflow.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @GET(Constants.GET_ME)
    suspend fun getTenantProfile(): Response<Tenant>

    @GET(Constants.GET_BILLS)
    suspend fun getBills(): Response<List<Bill>>

    @GET(Constants.GET_PAYMENTS)
    suspend fun getPayments(): Response<List<Payment>>

    @GET(Constants.GET_NOTIFICATIONS)
    suspend fun getNotifications(): Response<List<Notification>>

    @PUT(Constants.READ_NOTIFICATION)
    suspend fun markNotificationAsRead(
        @Path("id") notificationId: Int
    ): Response<Void>
}
