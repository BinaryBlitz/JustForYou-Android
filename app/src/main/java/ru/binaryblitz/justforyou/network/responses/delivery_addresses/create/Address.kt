package ru.binaryblitz.justforyou.network.responses.delivery_addresses.create

data class Address(
    val id: Int? = null,
    val house: String? = null,
    val latitude: Double? = null,
    val entrance: Int? = null,
    val floor: Int? = null,
    val content: String? = null,
    val apartment: Int? = null,
    val longitude: Double? = null
)
