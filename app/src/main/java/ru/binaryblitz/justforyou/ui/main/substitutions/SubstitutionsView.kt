package ru.binaryblitz.justforyou.ui.main.substitutions

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.binaryblitz.justforyou.network.responses.substitutions.Substitutions
import ru.binaryblitz.justforyou.ui.base.BaseLCEView

@StateStrategyType(AddToEndSingleStrategy::class)
interface SubstitutionsView : MvpView, BaseLCEView {
  fun showSubs(promotions: List<Substitutions>)
}
