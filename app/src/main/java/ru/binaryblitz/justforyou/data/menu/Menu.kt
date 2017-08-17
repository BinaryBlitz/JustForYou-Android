package ru.binaryblitz.justforyou.data.menu

data class Menu(
    val id: Int? = null,
    val position: Int? = null,
    val calories: Double? = null,
    val items: List<MenuItem>? = null
)
