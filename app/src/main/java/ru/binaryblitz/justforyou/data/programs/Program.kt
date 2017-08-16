package ru.binaryblitz.justforyou.data.programs

import com.squareup.moshi.Json

data class Program(
    val preview: String? = null,
    val unit: String? = null,
    @Json(name = "individual_price")
    val individualPrice: Boolean? = null,
    @Json(name = "secondary_price")
    val secondaryPrice: Int? = null,
    val prescription: List<String?>? = null,
    val imageUrl: String? = null,
    val name: String? = null,
    val description: String? = null,
    val threshold: Int? = null,
    val id: Int? = null,
    @Json(name = "block_id")
    val blockId: Int? = null,
    @Json(name = "primary_price")
    val primaryPrice: Int? = null
)
