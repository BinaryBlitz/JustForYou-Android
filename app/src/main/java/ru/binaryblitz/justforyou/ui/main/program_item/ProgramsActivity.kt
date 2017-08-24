package ru.binaryblitz.justforyou.ui.main.program_item

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.view.ViewPager
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.rd.animation.type.AnimationType
import kotlinx.android.synthetic.main.activity_program.pageIndicatorView
import kotlinx.android.synthetic.main.activity_program.programsCoordinator
import kotlinx.android.synthetic.main.activity_program.programsProgress
import kotlinx.android.synthetic.main.activity_program.programsViewPager
import kotlinx.android.synthetic.main.activity_program.toolbar
import kotlinx.android.synthetic.main.toolbar_cart_icon.badgeCount
import kotlinx.android.synthetic.main.toolbar_cart_icon.cartView
import ru.binaryblitz.justforyou.R
import ru.binaryblitz.justforyou.components.Extras
import ru.binaryblitz.justforyou.data.programs.Block
import ru.binaryblitz.justforyou.data.programs.Program
import ru.binaryblitz.justforyou.ui.base.BaseActivity
import ru.binaryblitz.justforyou.ui.main.ViewPagerAdapter
import ru.binaryblitz.justforyou.ui.router.Router

class ProgramsActivity : BaseActivity(), ProgramsView {
  @InjectPresenter
  lateinit var presenter: ProgramsPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_program)

    val block: Block = intent.getParcelableExtra<Block>(Extras.EXTRA_BLOCK)
    presenter.getBlockPrograms(block.id!!)
    blockName = block.name!!

    initViewElements(block.name)
  }

  private fun initViewElements(title: String) {
    setSupportActionBar(toolbar)
    toolbar.title = title
    toolbar.setNavigationIcon(R.drawable.ic_arrow_back24b)
    toolbar.setNavigationOnClickListener { onBackPressed() }
    initCartBadgeIcon()
  }

  private fun setupViewPager(viewPager: ViewPager, programs: List<Program>) {
    val adapter = ViewPagerAdapter(supportFragmentManager)
    for ((index) in programs.withIndex()) {
      adapter.addFragment(ProgramItemFragment.getInstance(
          programs[index]), "block")
    }

    viewPager.adapter = adapter
    pageIndicatorView.setViewPager(viewPager)
    pageIndicatorView.radius = 4
    pageIndicatorView.setAnimationType(AnimationType.SLIDE)
    pageIndicatorView.selectedColor = resources.getColor(R.color.primary)
    pageIndicatorView.unselectedColor = resources.getColor(R.color.colorAccent)
  }

  override fun showProgress() {
    programsProgress.visibility = View.VISIBLE
    programsViewPager.visibility = View.GONE
  }

  override fun hideProgress() {
    programsProgress.visibility = View.GONE
    programsViewPager.visibility = View.VISIBLE
  }

  override fun showError(message: String) {
    Snackbar.make(programsCoordinator, message, Snackbar.LENGTH_LONG).show()
  }

  override fun showPrograms(programs: List<Program>) {
    setupViewPager(programsViewPager, programs)
  }

  override fun initCartBadgeIcon() {
    updateCartBadgeCount(badgeCount, cartProgramsLocalStorage.getCartPrograms().size)
    cartView.setOnClickListener { Router.openCartScreen(this) }
  }

  override fun onResume() {
    super.onResume()
    updateCartBadgeCount(badgeCount, cartProgramsLocalStorage.getCartPrograms().size)
  }

  companion object {
    var blockName: String = ""
  }

}
