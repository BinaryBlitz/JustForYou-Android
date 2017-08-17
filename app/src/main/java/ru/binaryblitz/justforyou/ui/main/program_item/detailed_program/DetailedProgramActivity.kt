package ru.binaryblitz.justforyou.ui.main.program_item.detailed_program

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detailed_program.coordinator
import kotlinx.android.synthetic.main.activity_detailed_program.detailedProgramImage
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
import ru.binaryblitz.justforyou.data.cart.CartLocalStorage
import ru.binaryblitz.justforyou.data.cart.ProgramsStorage
import ru.binaryblitz.justforyou.data.programs.Program
import ru.binaryblitz.justforyou.ui.main.ViewPagerAdapter
import ru.binaryblitz.justforyou.ui.main.program_item.detailed_program.pages.about.AboutFragment
import ru.binaryblitz.justforyou.ui.main.program_item.detailed_program.pages.description.DescriptionFragment
import ru.binaryblitz.justforyou.ui.main.program_item.detailed_program.pages.menu.MenuFragment

class DetailedProgramActivity : AppCompatActivity() {
  var cartProgramsLocalStorage: CartLocalStorage = ProgramsStorage()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_detailed_program)
    initViewElements()
  }

  private fun initViewElements() {
    val program: Program = intent.getParcelableExtra(Extras.EXTRA_PROGRAM)
    Picasso.with(this).load(program.imageUrl).fit().centerCrop().into(detailedProgramImage)
    toolbar.title = ""
    programTitle.text = program.name
    toolbar.setNavigationIcon(drawable.ic_arrow_back24b)
    toolbar.setNavigationOnClickListener { onBackPressed() }
    programPricePerDay.text = getString(R.string.per_one_day) + "${program.primaryPrice}"
    programPricePerWeek.text = getString(R.string.per_ten_days) + "${program.secondaryPrice}"
    proceedProgramButton.setOnClickListener {
      cartProgramsLocalStorage.addProgramToCart(program.name, 1, 1, program)
      Snackbar.make(coordinator, getString(R.string.cart_add), Snackbar.LENGTH_LONG).show()
    }

    setupViewPager(detailedProgramViewPager, program)
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
