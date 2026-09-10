package com.rentflow.util

object Constants {
    // API Endpoints
    // For Android Emulator, 10.0.2.2 points to the host machine's localhost
    const val BASE_URL = "http://10.0.2.2:3000/api/v1/"
    
    // API Paths
    const val LOGIN_TENANT = "auth/tenant/login"
    const val GET_ME = "tenants/me"
    const val GET_BILLS = "tenants/me/bills"
    const val GET_PAYMENTS = "tenants/me/payments"
    const val GET_NOTIFICATIONS = "tenants/me/notifications"
    const val READ_NOTIFICATION = "notifications/{id}/read"
}
