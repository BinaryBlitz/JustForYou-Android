package ru.binaryblitz.justforyou.data.cart

import ru.binaryblitz.justforyou.data.programs.Program

interface CartLocalStorage {
  fun getCartPrograms(): List<CartModel>
  fun addProgramToCart(blockName: String, days: Int, price: Int, program: Program)
  fun removeProgramFromCart(programId: Int)
}
