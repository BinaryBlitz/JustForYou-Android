package ru.binaryblitz.justforyou.ui.main.purchases

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.binaryblitz.justforyou.network.responses.delivery_addresses.create.Address
import ru.binaryblitz.justforyou.network.responses.purchases.PurchasesResponse
import ru.binaryblitz.justforyou.ui.base.BaseLCEView

@StateStrategyType(AddToEndSingleStrategy::class)
interface PurchasesView : MvpView, BaseLCEView {
  fun showPurchases(addresses: List<PurchasesResponse>)
}
