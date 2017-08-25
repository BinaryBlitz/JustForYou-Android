package ru.binaryblitz.justforyou.ui.main.map

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.binaryblitz.justforyou.network.responses.map.ResultsItem
import ru.binaryblitz.justforyou.ui.base.BaseLCEView

@StateStrategyType(AddToEndSingleStrategy::class)
interface MapAddressView : MvpView, BaseLCEView {
  fun showAddress(address: ResultsItem)
  fun showLocation(address: ResultsItem)
  fun successAddressAddition()
  fun showAddressInfo()
  fun hideAddressInfo()
}
