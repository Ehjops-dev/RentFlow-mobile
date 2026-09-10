package com.rentflow.model

import com.google.gson.annotations.SerializedName

data class Notification(
    @SerializedName("id") val id: Int,
    @SerializedName("type") val type: String,
    @SerializedName("message") val message: String,
    @SerializedName("sent_at") val sentAt: String,
    @SerializedName("read_status") val readStatus: Int
)
