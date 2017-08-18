package ru.binaryblitz.justforyou.ui.main

import android.app.Activity
import android.os.Bundle
import com.crashlytics.android.Crashlytics
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import io.fabric.sdk.android.Fabric
import kotlinx.android.synthetic.main.activity_main.bottomBar
import kotlinx.android.synthetic.main.activity_main.tabsViewPager
import kotlinx.android.synthetic.main.activity_main.toolbar
import ru.binaryblitz.justforyou.R
import ru.binaryblitz.justforyou.data.user.UserProfileStorage
import ru.binaryblitz.justforyou.data.user.UserStorageImpl
import ru.binaryblitz.justforyou.ui.base.BaseActivity
import ru.binaryblitz.justforyou.ui.main.blocks.ProgramsFragment
import ru.binaryblitz.justforyou.ui.main.support.SupportFragment
import ru.binaryblitz.justforyou.ui.router.Router


/**
 * The main activity of app that controls the transitions between tabs from bottom bar
 */
class MainActivity : BaseActivity() {
  private val PROGRAMMS_TAB: Int = 0
  private val CALENDAR_TAB: Int = 1
  private val SUPPORT_TAB: Int = 2
  private var userProfileStorage: UserProfileStorage = UserStorageImpl()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Fabric.with(this, Crashlytics())
    checkIfAuthorized()
  }

  private fun checkIfAuthorized() {
    val userProfileStorage: UserProfileStorage = UserStorageImpl()
    val ifNotAuthorized: Boolean = userProfileStorage.getToken().isNullOrEmpty()
    if (ifNotAuthorized) {
      finish()
      Router.openStartScreen(this)
    } else {
      setContentView(R.layout.activity_main)
      initViewElements()
    }
  }

  private fun initViewElements() {
    val adapter = ViewPagerAdapter(supportFragmentManager)
    adapter.addFragment(ProgramsFragment.getInstance(), "programs_tag")
    adapter.addFragment(EmptyFragment(), "empty_tag")
    adapter.addFragment(SupportFragment(), "empty_tag")
    adapter.addFragment(EmptyFragment(), "empty_tag")
    tabsViewPager.offscreenPageLimit = 4
    tabsViewPager.adapter = adapter
    bottomBar.selectTabAtPosition(PROGRAMMS_TAB)
    bottomBar.setOnTabSelectListener { position ->
      when (position) {
        R.id.action_programms -> showTab("Программы", PROGRAMMS_TAB)
        R.id.action_delivery -> showTab("Доставка", CALENDAR_TAB)
        R.id.action_support -> showTab("Поддержка", SUPPORT_TAB)
      }
    }
    setSupportActionBar(toolbar)
    initDrawer(toolbar, this)
  }

  private fun showTab(title: String, position: Int) {
    toolbar.title = title
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
