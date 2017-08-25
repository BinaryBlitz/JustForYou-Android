package ru.binaryblitz.justforyou.network.responses.map

import com.squareup.moshi.Json

data class AddressComponentsItem(
    val types: List<String>? = null,
    @Json(name = "short_name")
    val shortName: String? = null,
    @Json(name = "long_name")
    val longName: String? = null
)
