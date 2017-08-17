package ru.binaryblitz.justforyou.data.cart

import ru.binaryblitz.justforyou.data.programs.Program

/**
 * Created by ilyasavin on 8/17/17.
 */
interface CartLocalStorage {
  fun getCartPrograms(): List<CartModel>
  fun addProgramToCart(blockName: String, days: Int, price: Int, program: Program)
  fun removeProgramFromCart(programId: Int)
}
