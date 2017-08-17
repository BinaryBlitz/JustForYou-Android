package ru.binaryblitz.justforyou.data.menu

import com.squareup.moshi.Json

data class MenuItem(
    @Json(name = "starts_at")
    val startsAt: String? = null,
    val weight: Int? = null,
    val id: Int? = null,
    val calories: Double? = null,
    @Json(name = "ends_at")
    val endsAt: String? = null,
    val content: String? = null
)
