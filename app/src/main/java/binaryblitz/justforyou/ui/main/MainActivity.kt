package binaryblitz.justforyou.ui.main

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import binaryblitz.justforyou.R
import binaryblitz.justforyou.data.user.UserProfileStorage
import binaryblitz.justforyou.data.user.UserStorageImpl
import binaryblitz.justforyou.ui.router.ScreenRouter
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import kotlinx.android.synthetic.main.activity_main.bottomBar
import kotlinx.android.synthetic.main.activity_main.tabsViewPager
import kotlinx.android.synthetic.main.activity_main.toolbar

/**
 * The main activity of app that controls the transitions between tabs from bottom bar
 */
class MainActivity : AppCompatActivity() {
  private val PROGRAMMS_TAB: Int = 0
  private val CALENDAR_TAB: Int = 1
  private val SUPPORT_TAB: Int = 2
  private var userProfileStorage: UserProfileStorage = UserStorageImpl()

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
    tabsViewPager.offscreenPageLimit = 4
    tabsViewPager.adapter = adapter
    bottomBar.selectTabAtPosition(PROGRAMMS_TAB)
    bottomBar.setOnTabSelectListener { position ->
      when (position) {
        R.id.action_programms -> showTab(PROGRAMMS_TAB)
        R.id.action_delivery -> showTab(CALENDAR_TAB)
        R.id.action_support -> showTab(SUPPORT_TAB)
      }
    }
    initDrawer(toolbar, this)
  }

  private fun showTab(position: Int) {
    tabsViewPager.currentItem = position
  }

  fun initDrawer(toolbar: android.support.v7.widget.Toolbar, activity: Activity) {
    val itemPrograms = PrimaryDrawerItem().withName("Программы")
    val itemPlaces = PrimaryDrawerItem().withName("Места")
    val itemPaymentHistory = PrimaryDrawerItem().withName("История платежей")
    val itemSubstitutions = PrimaryDrawerItem().withName("Замены")
    val itemSpecial = PrimaryDrawerItem().withName("Акции")

    val accountHeader: AccountHeader = AccountHeaderBuilder()
        .withActivity(this)
        .addProfiles(
            ProfileDrawerItem().withName(userProfileStorage.getUser().firstName).withEmail(
                userProfileStorage.getUser().email).withIcon(
                resources.getDrawable(R.drawable.ic_person_24))
        )
        .withHeaderBackground(R.color.colorPrimary)
        .build()

    val result = DrawerBuilder()
        .withActivity(activity)
        .withToolbar(toolbar)
        .withDisplayBelowStatusBar(true)
        .withTranslucentStatusBar(true)
        .withAccountHeader(accountHeader)
        .withSelectedItem(-1)
        .addDrawerItems(
            itemPrograms,
            itemPlaces,
            itemPaymentHistory,
            DividerDrawerItem(),
            itemSubstitutions,
            itemSpecial
        )
        .build()
  }


}
