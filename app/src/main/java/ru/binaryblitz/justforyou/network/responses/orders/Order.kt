package ru.binaryblitz.justforyou.network.responses.orders

import com.squareup.moshi.Json

data class Order(
    @Json(name = "line_items_attributes")
    val lineItemsAttributes: List<LineItemsAttributesItem>,
    @Json(name = "phone_number")
    val phoneNumber: String
)
