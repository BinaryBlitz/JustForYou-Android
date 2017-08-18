package ru.binaryblitz.justforyou.ui.main.cart

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.binaryblitz.justforyou.data.cart.CartModel

@StateStrategyType(AddToEndSingleStrategy::class)
interface CartView : MvpView {
  fun showPrograms(programs: List<CartModel>)
}
