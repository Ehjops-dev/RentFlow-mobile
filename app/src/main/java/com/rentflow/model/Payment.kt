package com.rentflow.model

import com.google.gson.annotations.SerializedName

data class Payment(
    @SerializedName("id") val id: Int,
    @SerializedName("amount") val amount: Double,
    @SerializedName("mpesa_transaction_code") val mpesaTransactionCode: String,
    @SerializedName("payment_date") val paymentDate: String,
    @SerializedName("bill_id") val billId: Int?
)
