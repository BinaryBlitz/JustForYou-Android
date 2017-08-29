package ru.binaryblitz.justforyou.ui.main.program_item

import android.app.Activity
import android.content.Intent
import ru.binaryblitz.justforyou.components.Extras
import ru.binaryblitz.justforyou.data.cart.CartLocalStorage
import ru.binaryblitz.justforyou.data.cart.ProgramsStorage
import ru.binaryblitz.justforyou.data.programs.Program

/**
 * Common presenter for views that can add programs to the cart
 */
class CartProgramPresenter(var context: Activity) {
  var cartProgramsLocalStorage: CartLocalStorage = ProgramsStorage()

  fun addProgramToCart(blockName: String, program: Program, days: Int) {
    cartProgramsLocalStorage.addProgramToCart(blockName, days,
        program.primaryPrice, program)
    val intent = Intent()
    intent.putExtra(Extras.EXTRA_PROGRAM, program.name)
    context.setResult(Activity.RESULT_OK, intent)
    context.finish()
  }
}
