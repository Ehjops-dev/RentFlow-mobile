package com.rentflow.model

import com.google.gson.annotations.SerializedName

data class Unit(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String?,
    @SerializedName("unit_number") val unitNumber: String?,
    @SerializedName("account_number") val accountNumber: String,
    @SerializedName("rent_amount") val rentAmount: Double
)
