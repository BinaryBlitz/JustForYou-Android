package ru.binaryblitz.justforyou.ui.login.onboarding.fragments

import ru.binaryblitz.justforyou.ui.login.onboarding.base.OnBoardingFragment


class DefaultOnboardingPage(pagePosition: Int, pageText: String,
    pageImage: Int) : OnBoardingFragment() {
  init {
    this.pagePosition = pagePosition
    this.pageText = pageText
    this.pageImage = pageImage
  }
}
