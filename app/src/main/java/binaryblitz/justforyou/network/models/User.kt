package binaryblitz.justforyou.network.models

import com.squareup.moshi.Json

data class User(
    @Json(name = "last_name")
    val lastName: String? = null,
    @Json(name = "phone_number")
    val phoneNumber: String? = null,
    @Json(name = "first_name")
    val firstName: String? = null,
    val email: String? = null
)
