package ru.binaryblitz.justforyou.network.responses.payment

import com.squareup.moshi.Json

data class Payment(
    @Json(name = "payment_url")
    val paymentUrl: String? = null,
    val paid: Boolean,
    val createdAt: String,
    val id: Int
)
