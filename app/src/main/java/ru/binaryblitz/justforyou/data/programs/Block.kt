package ru.binaryblitz.justforyou.data.programs

import com.squareup.moshi.Json
import io.mironov.smuggler.AutoParcelable

data class Block(
		@Json(name = "programs_count")
    val programsCount: Int? = null,
    val color: String? = null,
    @Json(name = "image_url")
    val imageUrl: String? = null,
    val name: String? = null,
    val id: Int? = null
) : AutoParcelable
