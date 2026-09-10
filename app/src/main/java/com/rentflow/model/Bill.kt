package com.rentflow.model

import com.google.gson.annotations.SerializedName

data class Bill(
    @SerializedName("id") val id: Int,
    @SerializedName("bill_type") val billType: String,
    @SerializedName("billing_period") val billingPeriod: String,
    @SerializedName("amount_due") val amountDue: Double,
    @SerializedName("due_date") val dueDate: String,
    @SerializedName("status") val status: String
)
