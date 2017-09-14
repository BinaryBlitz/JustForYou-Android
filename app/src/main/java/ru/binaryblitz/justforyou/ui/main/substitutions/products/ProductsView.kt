package ru.binaryblitz.justforyou.ui.main.substitutions.products

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.binaryblitz.justforyou.network.responses.product_types.ProductTypes
import ru.binaryblitz.justforyou.ui.base.BaseLCEView

@StateStrategyType(AddToEndSingleStrategy::class)
interface ProductsView : MvpView, BaseLCEView {
  fun showProducts(promotions: List<ProductTypes>)
  fun successAddition()
}
