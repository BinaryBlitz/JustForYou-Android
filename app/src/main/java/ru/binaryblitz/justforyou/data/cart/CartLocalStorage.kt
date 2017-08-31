package ru.binaryblitz.justforyou.data.cart

import ru.binaryblitz.justforyou.data.programs.Program
import ru.binaryblitz.justforyou.network.responses.orders.DeliveriesItem

interface CartLocalStorage {
  fun getCartPrograms(): List<CartModel>
  fun addProgramToCart(blockName: String, days: Int, price: Int, program: Program,
      deliveries: List<DeliveriesItem>?)
  fun removeProgramFromCart(programId: Int)
  fun clear()
}
