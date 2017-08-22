package ru.binaryblitz.justforyou.ui.main.cart

import com.arellomobile.mvp.InjectViewState
import ru.binaryblitz.justforyou.data.cart.CartLocalStorage
import ru.binaryblitz.justforyou.data.cart.ProgramsStorage
import ru.binaryblitz.justforyou.di.JustForYouApp
import ru.binaryblitz.justforyou.ui.base.BasePresenter

@InjectViewState
class CartPresenter : BasePresenter<CartView>() {
  var cartProgramsLocalStorage: CartLocalStorage = ProgramsStorage()

  init {
    JustForYouApp.appComponent?.inject(this)
  }

  fun getProgramsFromCart() {
    viewState.showPrograms(cartProgramsLocalStorage.getCartPrograms())
  }
}
