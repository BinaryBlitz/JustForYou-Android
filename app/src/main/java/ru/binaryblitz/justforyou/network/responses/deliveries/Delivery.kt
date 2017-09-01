package ru.binaryblitz.justforyou.network.responses.deliveries

import com.squareup.moshi.Json
import ru.binaryblitz.justforyou.network.responses.delivery_addresses.create.Address
import ru.binaryblitz.justforyou.network.responses.purchases.PurchasesResponse

data class Delivery(
    val address: Address? = null,
    val purchase: PurchasesResponse? = null,
    @Json(name = "scheduled_for")
    val scheduledFor: String? = null,
    val comment: String? = null,
    val id: Int? = null
)
