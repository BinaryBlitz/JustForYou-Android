package ru.binaryblitz.justforyou.ui.main.program_item.detailed_program

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.view.ViewPager
import android.view.View
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detailed_program.coordinator
import kotlinx.android.synthetic.main.activity_detailed_program.detailedProgramCollapsingView
import kotlinx.android.synthetic.main.activity_detailed_program.detailedProgramImage
import kotlinx.android.synthetic.main.activity_detailed_program.proceedProgramButton
import kotlinx.android.synthetic.main.activity_detailed_program.programTitle
import kotlinx.android.synthetic.main.activity_detailed_program.toolbar
import kotlinx.android.synthetic.main.content_detailed_program.detailedProgramViewPager
import kotlinx.android.synthetic.main.content_detailed_program.programTabsLayout
import kotlinx.android.synthetic.main.fragment_program_item.programPricePerDay
import kotlinx.android.synthetic.main.fragment_program_item.programPricePerWeek
import kotlinx.android.synthetic.main.toolbar_cart_icon.badgeCount
import kotlinx.android.synthetic.main.toolbar_cart_icon.cartView
import ru.binaryblitz.justforyou.R
import ru.binaryblitz.justforyou.R.drawable
import ru.binaryblitz.justforyou.R.string
import ru.binaryblitz.justforyou.components.Constants
import ru.binaryblitz.justforyou.components.Extras
import ru.binaryblitz.justforyou.data.programs.Program
import ru.binaryblitz.justforyou.ui.base.BaseCartActivity
import ru.binaryblitz.justforyou.ui.main.ViewPagerAdapter
import ru.binaryblitz.justforyou.ui.main.program_item.detailed_program.pages.about.AboutFragment
import ru.binaryblitz.justforyou.ui.main.program_item.detailed_program.pages.description.DescriptionFragment
import ru.binaryblitz.justforyou.ui.main.program_item.detailed_program.pages.menu.MenuFragment
import ru.binaryblitz.justforyou.ui.router.Router

class DetailedProgramActivity : BaseCartActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_detailed_program)
    initViewElements()
  }

  override fun onResume() {
    super.onResume()
    updateCartBadgeCount(badgeCount, cartProgramsLocalStorage.getCartPrograms().size)
  }

  private fun initViewElements() {
    detailedProgramCollapsingView.setExpandedTitleColor(Color.parseColor("#00FFFFFF"))

    val program: Program = intent.getParcelableExtra(Extras.EXTRA_PROGRAM)
    Picasso.with(this).load(program.imageUrl).placeholder(
        R.drawable.program_placeholder).fit().centerCrop().into(detailedProgramImage)
    setSupportActionBar(toolbar)
    toolbar.title = ""
    detailedProgramCollapsingView.title = program.name
    programTitle.text = program.name
    toolbar.setNavigationIcon(drawable.ic_arrow_back24b)
    toolbar.setNavigationOnClickListener { onBackPressed() }
    programPricePerDay.text = getString(R.string.per_one_day) + "${program.primaryPrice}"
    programPricePerWeek.text = getString(R.string.per_ten_days) + "${program.secondaryPrice}"
    if (program.individualPrice) {
      programPricePerDay.text = getString(string.individual_price)
      programPricePerWeek.visibility = View.GONE
      proceedProgramButton.text = getString(string.call_manager)
    }
    proceedProgramButton.setOnClickListener {
      if (program.individualPrice) {
        Router.dialPhoneNumber(this, Constants.supportNumber)
        return@setOnClickListener
      }
      Router.openOrderScreen(this, program, intent.getStringExtra(Extras.EXTRA_PROGRAM_BLOCK_NAME),
          Extras.orderRequestCode)
    }

    setupViewPager(detailedProgramViewPager, program)
    initCartBadgeIcon()
  }

  private fun setupViewPager(viewPager: ViewPager, program: Program) {
    val adapter = ViewPagerAdapter(supportFragmentManager)
    adapter.addFragment(AboutFragment.getInstance(program), "Для кого")
    adapter.addFragment(DescriptionFragment.getInstance(program), "Описание")
    adapter.addFragment(MenuFragment.getInstance(program), "Меню")

    viewPager.adapter = adapter
    programTabsLayout.setupWithViewPager(viewPager)
  }

  override fun initCartBadgeIcon() {
    updateCartBadgeCount(badgeCount, cartProgramsLocalStorage.getCartPrograms().size)
    cartView.setOnClickListener { Router.openCartScreen(this) }
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (resultCode == Activity.RESULT_OK && requestCode == Extras.orderRequestCode) {
      val program = data?.getStringExtra(Extras.EXTRA_PROGRAM)
      Snackbar.make(coordinator, String.format(getString(string.cart_add), program),
          Snackbar.LENGTH_LONG).setAction(getString(string.go_to_cart),
          { Router.openCartScreen(this) }).show()
    }
  }
}
