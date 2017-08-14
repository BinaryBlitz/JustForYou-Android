package ru.binaryblitz.justforyou.network.responses

import com.squareup.moshi.Json

data class CreateTokenResponse(
    @Json(name = "phone_number")
    val phoneNumber: String? = null,
    val token: String? = null
)
