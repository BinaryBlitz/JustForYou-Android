package ru.binaryblitz.justforyou.ui.login.onboarding

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.View
import ru.binaryblitz.justforyou.R
import ru.binaryblitz.justforyou.R.mipmap
import com.rd.animation.type.AnimationType
import kotlinx.android.synthetic.main.activity_on_boarding.nextButton
import kotlinx.android.synthetic.main.activity_on_boarding.pageIndicatorView
import kotlinx.android.synthetic.main.activity_on_boarding.viewPager
import ru.binaryblitz.justforyou.ui.login.onboarding.base.OnBoardingFragment
import ru.binaryblitz.justforyou.ui.login.onboarding.fragments.DefaultOnboardingPage
import ru.binaryblitz.justforyou.ui.login.onboarding.fragments.WelcomePage
import ru.binaryblitz.justforyou.ui.router.Router

class OnBoardingActivity : AppCompatActivity(), ViewPager.OnPageChangeListener {
  val PAGES_COUNT = 4

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_on_boarding)

    val viewPager = initOnBoardingPages()
    initViewElements(viewPager)
  }

  private fun initViewElements(viewPager: ViewPager) {
    pageIndicatorView.setViewPager(viewPager)
    pageIndicatorView.radius = 4
    pageIndicatorView.setAnimationType(AnimationType.SLIDE)
    pageIndicatorView.selectedColor = resources.getColor(R.color.whiteColor)
    pageIndicatorView.unselectedColor = resources.getColor(R.color.colorAccent)

    nextButton.setOnClickListener { Router.openMainScreen(this) }
  }

  private fun initOnBoardingPages(): ViewPager {
    val pages = arrayOf(
        WelcomePage.newInstance(),
        DefaultOnboardingPage(1, "TEST",
            mipmap.ic_launcher),
        DefaultOnboardingPage(2, "TEST",
            R.mipmap.ic_launcher),
        DefaultOnboardingPage(3, "TEST",
            R.mipmap.ic_launcher))
    return initViewPager(pages)
  }

  private fun initViewPager(pages: Array<OnBoardingFragment>): ViewPager {
    viewPager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
      override fun getItem(position: Int): Fragment? {
        when (position) {
          0 -> return pages[position]
          1 -> return pages[position]
          2 -> return pages[position]
          3 -> return pages[position]
        }
        return null
      }

      override fun getCount(): Int {
        return PAGES_COUNT
      }
    }
    for (page in pages) {
      viewPager.addOnPageChangeListener(page)
    }
    viewPager.addOnPageChangeListener(this)
    return viewPager
  }

  override fun onPageScrollStateChanged(p0: Int) {
  }

  override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
  }

  override fun onPageSelected(p0: Int) {
    if (p0 == PAGES_COUNT - 1)
      nextButton.visibility = View.VISIBLE
  }
}
