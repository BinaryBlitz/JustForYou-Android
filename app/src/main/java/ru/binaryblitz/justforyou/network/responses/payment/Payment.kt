package ru.binaryblitz.justforyou.network.responses.payment

import com.squareup.moshi.Json

data class Payment(
    @Json(name = "payment_url")
    val paymentUrl: String? = null,
    val paid: Boolean? = null,
    val createdAt: String? = null,
    val id: Int? = null
)
