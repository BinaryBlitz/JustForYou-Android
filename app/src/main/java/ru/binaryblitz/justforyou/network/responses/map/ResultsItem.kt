package ru.binaryblitz.justforyou.network.responses.map

import com.squareup.moshi.Json

data class ResultsItem(
		@Json(name = "formatted_address")
    val formattedAddress: String? = null,
    val types: List<String?>? = null,
    val geometry: Geometry,
    @Json(name = "address_components")
    val addressComponents: List<AddressComponentsItem>? = null,
    val placeId: String? = null
)
