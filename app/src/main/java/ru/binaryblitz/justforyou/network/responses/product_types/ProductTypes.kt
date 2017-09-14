package ru.binaryblitz.justforyou.network.responses.product_types

data class ProductTypes(
    val name: String? = null,
    val id: Int? = null,
    val products: List<ProductsItem>? = null
)
