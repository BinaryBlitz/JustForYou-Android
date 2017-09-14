package ru.binaryblitz.justforyou.network.responses.substitutions

import ru.binaryblitz.justforyou.network.responses.product_types.ProductsItem

data class Substitutions(
    val product: ProductsItem? = null,
    val createdAt: String? = null,
    val id: Int? = null
)
