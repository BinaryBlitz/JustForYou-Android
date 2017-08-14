package ru.binaryblitz.justforyou.network.responses

import com.squareup.moshi.Json

data class VerifyTokenResponse(
    @Json(name = "api_token")
    val apiToken: String? = null
)
