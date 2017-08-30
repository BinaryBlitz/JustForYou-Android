package ru.binaryblitz.justforyou.network.responses.payment_cards

data class PaymentCard(
    val number: String,
    val holder: String,
    val id: Int
)
