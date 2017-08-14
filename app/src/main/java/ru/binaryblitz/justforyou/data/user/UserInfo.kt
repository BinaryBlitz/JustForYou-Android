package ru.binaryblitz.justforyou.data.user

import com.squareup.moshi.Json

data class UserInfo(
    @Json(name = "last_name")
    val lastName: String,
    @Json(name = "first_name")
    val firstName: String,
    val balance: Int,
    @Json(name = "phone_number")
    val phoneNumber: String,
    val id: Int,
    val email: String,
    @Json(name = "api_token")
    var apiToken: String
)
