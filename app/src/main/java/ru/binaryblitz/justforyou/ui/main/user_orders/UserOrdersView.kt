package ru.binaryblitz.justforyou.ui.main.user_orders

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.binaryblitz.justforyou.network.responses.orders.Order
import ru.binaryblitz.justforyou.ui.base.BaseLCEView

@StateStrategyType(AddToEndSingleStrategy::class)
interface UserOrdersView : MvpView, BaseLCEView {
  fun showOrders(addresses: List<Order>)
}
