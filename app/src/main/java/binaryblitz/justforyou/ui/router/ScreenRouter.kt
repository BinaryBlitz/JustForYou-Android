package binaryblitz.justforyou.ui.router

import android.app.Activity
import android.content.Intent
import binaryblitz.justforyou.ui.login.LoginActivity
import binaryblitz.justforyou.ui.login.onboarding.OnBoardingActivity
import binaryblitz.justforyou.ui.main.MainActivity

/**
 * Class that holds all screen navigation and transitions.
 */
class ScreenRouter {
  companion object {
    fun openMainScreen(context: Activity) {
      val intent = Intent(context, MainActivity::class.java)
      context.finish()
      context.startActivity(intent)
    }

    fun openOnboardingScreen(context: Activity) {
      val intent = Intent(context, OnBoardingActivity::class.java)
      context.finish()
      context.startActivity(intent)
    }

    fun openStartScreen(context: Activity) {
      val intent = Intent(context, LoginActivity::class.java)
      context.startActivity(intent)
    }
  }
}