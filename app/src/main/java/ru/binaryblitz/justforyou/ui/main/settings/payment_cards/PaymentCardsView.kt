package ru.binaryblitz.justforyou.ui.main.settings.payment_cards

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.binaryblitz.justforyou.network.responses.payment_cards.PaymentCard
import ru.binaryblitz.justforyou.ui.base.BaseLCEView

@StateStrategyType(AddToEndSingleStrategy::class)
interface PaymentCardsView : MvpView, BaseLCEView {
  fun showCards(addresses: List<PaymentCard>)
}
