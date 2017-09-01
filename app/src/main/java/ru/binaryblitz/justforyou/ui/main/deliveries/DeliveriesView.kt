package ru.binaryblitz.justforyou.ui.main.deliveries

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.binaryblitz.justforyou.network.responses.deliveries.Delivery
import ru.binaryblitz.justforyou.ui.base.BaseLCEView

@StateStrategyType(AddToEndSingleStrategy::class)
interface DeliveriesView : MvpView, BaseLCEView {
  fun showDeliveries(deliveries: List<Delivery>)
}
