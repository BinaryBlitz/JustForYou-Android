package ru.binaryblitz.justforyou.network.responses.purchases

import com.squareup.moshi.Json
import ru.binaryblitz.justforyou.data.programs.Program

data class PurchasesResponse(
    @Json(name = "deliveries_count")
    val deliveriesCount: Int? = null,
    @Json(name = "number_of_days")
    val numberOfDays: Int? = null,
    val createdAt: String? = null,
    val id: Int? = null,
    val program: Program? = null
)
