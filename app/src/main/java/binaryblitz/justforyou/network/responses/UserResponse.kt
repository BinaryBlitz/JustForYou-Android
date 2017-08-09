package binaryblitz.justforyou.network.responses

import com.squareup.moshi.Json

data class UserResponse(
    val balance: Int? = null,
    @Json(name = "api_token")
    val apiToken: String? = null,
    @Json(name = "last_name")
    val lastName: String? = null,
    @Json(name = "phone_number")
    val phoneNumber: String? = null,
    val id: Int? = null,
    @Json(name = "first_name")
    val firstName: String? = null,
    val email: String? = null
)
