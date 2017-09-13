package ru.binaryblitz.justforyou.ui.main.promotions

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.binaryblitz.justforyou.network.responses.orders.PurchaseItem
import ru.binaryblitz.justforyou.network.responses.promotions.Promotion
import ru.binaryblitz.justforyou.ui.base.BaseLCEView

@StateStrategyType(AddToEndSingleStrategy::class)
interface PromotionsView : MvpView, BaseLCEView {
  fun showPromotions(promotions: List<Promotion>)
}
