package ru.binaryblitz.justforyou.ui.main.program_item.detailed_program.pages.menu

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.binaryblitz.justforyou.data.menu.Menu
import ru.binaryblitz.justforyou.ui.base.BaseLCEView

@StateStrategyType(AddToEndSingleStrategy::class)
interface MenuView : MvpView, BaseLCEView {
  fun showMenu(menu: List<Menu>)
}
