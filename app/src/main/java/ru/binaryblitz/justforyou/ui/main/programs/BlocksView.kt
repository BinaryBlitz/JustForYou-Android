package ru.binaryblitz.justforyou.ui.main.programs

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.binaryblitz.justforyou.data.programs.Block
import ru.binaryblitz.justforyou.ui.base.BaseLCEView

@StateStrategyType(AddToEndSingleStrategy::class)
interface BlocksView : MvpView, BaseLCEView {
  fun showBlocks(blocks: List<Block>)
}
