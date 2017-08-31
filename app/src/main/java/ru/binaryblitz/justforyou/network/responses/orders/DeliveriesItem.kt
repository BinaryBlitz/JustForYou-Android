package ru.binaryblitz.justforyou.network.responses.orders

import com.squareup.moshi.Json

data class DeliveriesItem(
    @Json(name = "address_id")
    val addressId: Int,
    @Json(name = "scheduled_for")
    val scheduledFor: String,
    val comment: String? = null
)
