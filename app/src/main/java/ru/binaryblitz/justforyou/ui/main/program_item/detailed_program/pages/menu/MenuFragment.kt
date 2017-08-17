package ru.binaryblitz.justforyou.ui.main.program_item.detailed_program.pages.menu

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import kotlinx.android.synthetic.main.fragment_menu.leftViewPagerButton
import kotlinx.android.synthetic.main.fragment_menu.menuContainer
import kotlinx.android.synthetic.main.fragment_menu.menuProgress
import kotlinx.android.synthetic.main.fragment_menu.menuViewPager
import kotlinx.android.synthetic.main.fragment_menu.rightViewPagerButton
import ru.binaryblitz.justforyou.R
import ru.binaryblitz.justforyou.data.menu.Menu
import ru.binaryblitz.justforyou.data.programs.Program
import ru.binaryblitz.justforyou.ui.main.ViewPagerAdapter


class MenuFragment : MvpAppCompatFragment(), MenuView {
  @InjectPresenter(type = PresenterType.LOCAL)
  lateinit var presenter: MenuPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_menu, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initViewElements(view)
    presenter.getMenu(arguments.getParcelable<Program>(ARG_PROGRAM).id!!)
  }

  fun initViewElements(view: View) {

  }


  private fun setupViewPager(viewPager: ViewPager, menu: List<Menu>) {
    val adapter = ViewPagerAdapter(activity.supportFragmentManager)
    for ((index) in menu.withIndex()) {
      adapter.addFragment(DayMenuFragment.getInstance(menu[index], index), "Меню")
    }
    viewPager.adapter = adapter

    leftViewPagerButton.setOnClickListener {
      var tab = viewPager.currentItem
      if (tab > 0) {
        tab--
        viewPager.currentItem = tab
      } else if (tab == 0) {
        viewPager.currentItem = tab
      }
    }

    rightViewPagerButton.setOnClickListener {
      var tab = viewPager.currentItem
      tab++
      viewPager.currentItem = tab
    }
  }

  override fun showMenu(menu: List<Menu>) {
    setupViewPager(menuViewPager, menu)
  }

  override fun showProgress() {
    menuProgress.visibility = View.VISIBLE
  }

  override fun hideProgress() {
    menuProgress.visibility = View.GONE
  }

  override fun showError(message: String) {
    Snackbar.make(menuContainer, message, Snackbar.LENGTH_SHORT).show()
  }

  companion object {
    val ARG_PROGRAM: String = "program_item"

    fun getInstance(type: Program): MenuFragment {
      val fragment = MenuFragment()
      val args = Bundle()
      args.putParcelable(
          ARG_PROGRAM, type)
      fragment.arguments = args
      return fragment
    }
  }
}
