package ru.binaryblitz.justforyou.ui.main.cart

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.binaryblitz.justforyou.data.cart.CartModel
import ru.binaryblitz.justforyou.network.responses.orders.OrderResponse
import ru.binaryblitz.justforyou.network.responses.payment_cards.PaymentCard
import ru.binaryblitz.justforyou.ui.base.BaseLCEView

@StateStrategyType(AddToEndSingleStrategy::class)
interface CartView : MvpView, BaseLCEView {
  fun showPrograms(programs: List<CartModel>)
  fun orderCreated(order: OrderResponse)
  fun openPaymentUrl(url: String)
  fun sendDeliveryDays()
  fun showPaymentCards(cards: List<PaymentCard>)
  fun showSuccessPaymentMessage()
  fun showPaymentProgress()
  fun hidePaymentProgress()
}
