package binaryblitz.justforyou.ui.login.onboarding.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import binaryblitz.justforyou.R
import binaryblitz.justforyou.ui.login.onboarding.base.OnBoardingFragment


class WelcomePage : OnBoardingFragment() {
  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    return inflater!!.inflate(R.layout.onboarding_welcome, container, false)
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
  }

  override var pagePosition: Int
    get() = PAGE_POSITION
    set(value: Int) {
      super.pagePosition = value
    }

  companion object {
    val PAGE_POSITION = 0

    fun newInstance(): WelcomePage {
      val args = Bundle()
      val fragment = WelcomePage()
      fragment.arguments = args
      return fragment
    }
  }
}

