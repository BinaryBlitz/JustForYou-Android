package ru.binaryblitz.justforyou.network.responses.orders

import com.squareup.moshi.Json

data class PaymentCardBody(
    @Json(name = "payment_card_id")
    val paymentCardId: Int
)
