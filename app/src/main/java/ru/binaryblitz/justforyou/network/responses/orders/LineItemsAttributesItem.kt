package ru.binaryblitz.justforyou.network.responses.orders

import com.squareup.moshi.Json

data class LineItemsAttributesItem(
    @Json(name = "number_of_days")
    val numberOfDays: Int? = null,
    @Json(name = "program_id")
    val programId: Int? = null
)
