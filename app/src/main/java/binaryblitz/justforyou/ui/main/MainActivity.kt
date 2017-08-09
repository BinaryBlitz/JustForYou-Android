package binaryblitz.justforyou.ui.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import binaryblitz.justforyou.R
import binaryblitz.justforyou.data.user.UserProfileStorage
import binaryblitz.justforyou.data.user.UserStorageImpl
import binaryblitz.justforyou.ui.router.ScreenRouter
import kotlinx.android.synthetic.main.activity_main.bottomBar
import kotlinx.android.synthetic.main.activity_main.tabsViewPager

/**
 * The main activity of app that controls the transitions between tabs from bottom bar
 */
class MainActivity : AppCompatActivity() {
  private val PROGRAMMS_TAB: Int = 0
  private val CALENDAR_TAB: Int = 1
  private val SUPPORT_TAB: Int = 2
  private val PROFILE_TAB: Int = 3

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    checkIfAuthorized()
  }

  private fun checkIfAuthorized() {
    val userProfileStorage: UserProfileStorage = UserStorageImpl()
    val ifNotAuthorized: Boolean = userProfileStorage.getToken().isNullOrEmpty()
    if (ifNotAuthorized) {
      finish()
      ScreenRouter.openStartScreen(this)
    } else {
      setContentView(R.layout.activity_main)
      initViewElements()
    }
  }

  private fun initViewElements() {
    val adapter = ViewPagerAdapter(supportFragmentManager)
    adapter.addFragment(EmptyFragment(), "empty_tag")
    adapter.addFragment(EmptyFragment(), "empty_tag")
    adapter.addFragment(EmptyFragment(), "empty_tag")
    adapter.addFragment(EmptyFragment(), "empty_tag")
    tabsViewPager.offscreenPageLimit = 5
    tabsViewPager.adapter = adapter
    bottomBar.selectTabAtPosition(PROGRAMMS_TAB)
    bottomBar.setOnTabSelectListener { position ->
      when (position) {
        R.id.action_programms -> showTab(PROGRAMMS_TAB)
        R.id.action_delivery -> showTab(CALENDAR_TAB)
        R.id.action_support-> showTab(SUPPORT_TAB)
        else -> showTab(PROFILE_TAB)
      }
    }
  }

  private fun showTab(position: Int) {
    tabsViewPager.currentItem = position
  }

}
