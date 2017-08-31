package ru.binaryblitz.justforyou.network.responses.orders

import com.squareup.moshi.Json

data class OrderResponse(
    @Json(name = "total_price")
    val totalPrice: Int,
    @Json(name = "created_at")
    val createdAt: String,
    val comment: String? = null,
    @Json(name = "phone_number")
    val phoneNumber: String,
    val id: Int
)
