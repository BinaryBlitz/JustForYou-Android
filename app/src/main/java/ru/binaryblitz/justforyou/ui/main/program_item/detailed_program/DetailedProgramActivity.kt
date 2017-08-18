package ru.binaryblitz.justforyou.ui.main.program_item.detailed_program

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetBehavior.BottomSheetCallback
import android.support.v4.view.ViewPager
import android.view.View
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detailed_program.addToCartButton
import kotlinx.android.synthetic.main.activity_detailed_program.coordinator
import kotlinx.android.synthetic.main.activity_detailed_program.dayPickerAlertSheet
import kotlinx.android.synthetic.main.activity_detailed_program.detailedProgramCollapsingView
import kotlinx.android.synthetic.main.activity_detailed_program.detailedProgramImage
import kotlinx.android.synthetic.main.activity_detailed_program.numberPicker
import kotlinx.android.synthetic.main.activity_detailed_program.proceedProgramButton
import kotlinx.android.synthetic.main.activity_detailed_program.programTitle
import kotlinx.android.synthetic.main.activity_detailed_program.toolbar
import kotlinx.android.synthetic.main.content_detailed_program.detailedProgramViewPager
import kotlinx.android.synthetic.main.content_detailed_program.programTabsLayout
import kotlinx.android.synthetic.main.fragment_program_item.programPricePerDay
import kotlinx.android.synthetic.main.fragment_program_item.programPricePerWeek
import ru.binaryblitz.justforyou.R
import ru.binaryblitz.justforyou.R.drawable
import ru.binaryblitz.justforyou.components.Extras
import ru.binaryblitz.justforyou.data.programs.Program
import ru.binaryblitz.justforyou.ui.base.BaseActivity
import ru.binaryblitz.justforyou.ui.main.ViewPagerAdapter
import ru.binaryblitz.justforyou.ui.main.program_item.CartProgramPresenter
import ru.binaryblitz.justforyou.ui.main.program_item.detailed_program.pages.about.AboutFragment
import ru.binaryblitz.justforyou.ui.main.program_item.detailed_program.pages.description.DescriptionFragment
import ru.binaryblitz.justforyou.ui.main.program_item.detailed_program.pages.menu.MenuFragment


class DetailedProgramActivity : BaseActivity() {
  lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
  lateinit var cartProgramPresenter: CartProgramPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_detailed_program)
    initViewElements()
  }

  private fun initViewElements() {
    cartProgramPresenter = CartProgramPresenter(this)
    detailedProgramCollapsingView.setExpandedTitleColor(Color.parseColor("#00FFFFFF"))

    val program: Program = intent.getParcelableExtra(Extras.EXTRA_PROGRAM)
    Picasso.with(this).load(program.imageUrl).fit().centerCrop().into(detailedProgramImage)
    setSupportActionBar(toolbar)
    toolbar.title = ""
    detailedProgramCollapsingView.title = program.name
    programTitle.text = program.name
    toolbar.setNavigationIcon(drawable.ic_arrow_back24b)
    toolbar.setNavigationOnClickListener { onBackPressed() }
    programPricePerDay.text = getString(R.string.per_one_day) + "${program.primaryPrice}"
    programPricePerWeek.text = getString(R.string.per_ten_days) + "${program.secondaryPrice}"
    proceedProgramButton.setOnClickListener {
      showDayPickerAlert()
    }
    bottomSheetBehavior = BottomSheetBehavior.from(dayPickerAlertSheet as View)
    bottomSheetBehavior.setBottomSheetCallback(object : BottomSheetCallback() {
      override fun onSlide(bottomSheet: View, slideOffset: Float) {
      }

      override fun onStateChanged(bottomSheet: View, newState: Int) {
        if (newState == BottomSheetBehavior.STATE_HIDDEN) {
          proceedProgramButton.visibility = View.VISIBLE
        }
      }
    }
    )
    hideDayPickerAlert()

    setupViewPager(detailedProgramViewPager, program)

    numberPicker.maxValue = 31
    numberPicker.minValue = 1

    addToCartButton.setOnClickListener {
      cartProgramPresenter.addProgramToCart(program, coordinator, cartProgramsLocalStorage,
          numberPicker)

      hideDayPickerAlert()
    }
  }

  fun showDayPickerAlert() {
    proceedProgramButton.visibility = View.GONE
    bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
  }

  fun hideDayPickerAlert() {
    bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
  }

  private fun setupViewPager(viewPager: ViewPager, program: Program) {
    val adapter = ViewPagerAdapter(supportFragmentManager)
    adapter.addFragment(AboutFragment.getInstance(program), "Для кого")
    adapter.addFragment(DescriptionFragment.getInstance(program), "Описание")
    adapter.addFragment(MenuFragment.getInstance(program), "Меню")

    viewPager.adapter = adapter
    programTabsLayout.setupWithViewPager(viewPager)
  }

}
