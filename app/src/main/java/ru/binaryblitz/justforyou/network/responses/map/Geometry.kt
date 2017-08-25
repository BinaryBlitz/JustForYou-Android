package ru.binaryblitz.justforyou.network.responses.map

data class Geometry(
    val location: Location,
    val locationType: String? = null
)
