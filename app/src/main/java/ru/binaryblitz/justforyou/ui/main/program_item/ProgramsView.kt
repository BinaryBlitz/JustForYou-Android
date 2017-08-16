package ru.binaryblitz.justforyou.ui.main.program_item

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.binaryblitz.justforyou.data.programs.Program
import ru.binaryblitz.justforyou.ui.base.BaseLCEView

@StateStrategyType(AddToEndSingleStrategy::class)
interface ProgramsView : MvpView, BaseLCEView {
  fun showPrograms(blocks: List<Program>)
}
