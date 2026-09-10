package com.rentflow.repository

import com.rentflow.model.*
import com.rentflow.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RentFlowRepository(private val apiService: ApiService) {

    suspend fun getTenantProfile(): Result<Tenant> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getTenantProfile()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to fetch tenant profile: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getBills(): Result<List<Bill>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getBills()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to fetch bills: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getPayments(): Result<List<Payment>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getPayments()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to fetch payments: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getNotifications(): Result<List<Notification>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getNotifications()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to fetch notifications: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun markNotificationAsRead(notificationId: Int): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.markNotificationAsRead(notificationId)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to mark notification as read: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
