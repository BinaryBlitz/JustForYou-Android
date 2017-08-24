package ru.binaryblitz.justforyou.ui.main.program_item

import android.content.Context
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.NumberPicker
import ru.binaryblitz.justforyou.R.string
import ru.binaryblitz.justforyou.data.cart.CartLocalStorage
import ru.binaryblitz.justforyou.data.programs.Program

/**
 * Common presenter for views that can add programs to the cart
 */
class CartProgramPresenter(var context: Context) {

  fun addProgramToCart(blockName: String, program: Program, view: View,
      cartProgramsLocalStorage: CartLocalStorage, numberPicker: NumberPicker) {
    cartProgramsLocalStorage.addProgramToCart(blockName, numberPicker.value,
        program.primaryPrice, program)
    Snackbar.make(view, String.format(context.getString(string.cart_add), program.name),
        Snackbar.LENGTH_LONG).show()
  }
}
