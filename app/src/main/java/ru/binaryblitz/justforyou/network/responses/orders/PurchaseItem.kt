package ru.binaryblitz.justforyou.network.responses.orders

import com.squareup.moshi.Json
import ru.binaryblitz.justforyou.data.programs.Program

data class PurchaseItem(
    @Json(name = "total_price")
    val totalPrice: Int? = null,
    @Json(name = "created_at")
    val createdAt: String? = null,
    val comment: Any? = null,
    @Json(name = "phone_number")
    val phoneNumber: String? = null,
    val id: Int? = null,
    val programs: List<Program>? = null
)
