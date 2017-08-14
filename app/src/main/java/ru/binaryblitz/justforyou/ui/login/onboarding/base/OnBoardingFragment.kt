package ru.binaryblitz.justforyou.ui.login.onboarding.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.base_onboarding_screen.pageContainer
import kotlinx.android.synthetic.main.base_onboarding_screen.pageImageView
import kotlinx.android.synthetic.main.base_onboarding_screen.pageTextView
import ru.binaryblitz.justforyou.R

abstract class OnBoardingFragment : Fragment(), ViewPager.OnPageChangeListener {
  open var pagePosition: Int = 0

  var pageText: String? = null
  var pageImage: Int? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    retainInstance = true
  }

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    return inflater?.inflate(R.layout.base_onboarding_screen, container, false)
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    pageTextView!!.text = pageText
    pageImageView!!.setImageResource(pageImage!!)
  }

  override fun onPageSelected(position: Int) {}

  override fun onPageScrollStateChanged(state: Int) {}

  override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    val layout = pageContainer ?: return
    val pagePosition = pagePosition
  }
}
