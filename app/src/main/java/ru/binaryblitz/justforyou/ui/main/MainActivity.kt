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
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem
import io.fabric.sdk.android.Fabric
import kotlinx.android.synthetic.main.activity_main.appBar
import kotlinx.android.synthetic.main.activity_main.bottomBar
import kotlinx.android.synthetic.main.activity_main.tabsViewPager
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.toolbar_cart_icon.badgeCount
import kotlinx.android.synthetic.main.toolbar_cart_icon.cartView
import ru.binaryblitz.justforyou.R
import ru.binaryblitz.justforyou.components.utils.AnimationBuilderHelper
import ru.binaryblitz.justforyou.data.user.UserProfileStorage
import ru.binaryblitz.justforyou.data.user.UserStorageImpl
import ru.binaryblitz.justforyou.ui.base.BaseCartActivity
import ru.binaryblitz.justforyou.ui.main.blocks.ProgramsFragment
import ru.binaryblitz.justforyou.ui.main.deliveries.DeliveriesFragment
import ru.binaryblitz.justforyou.ui.main.support.SupportFragment
import ru.binaryblitz.justforyou.ui.router.Router


/**
 * The main activity of app that controls the transitions between tabs from bottom bar
 */
class MainActivity : BaseCartActivity() {
  private val PROGRAMMS_TAB: Int = 0
  private val CALENDAR_TAB: Int = 1
  private val SUPPORT_TAB: Int = 2
  val userProfileStorage: UserProfileStorage = UserStorageImpl()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Fabric.with(this, Crashlytics())
    checkIfAuthorized()
  }

  private fun checkIfAuthorized() {
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
    AnimationBuilderHelper.initMainView(this, appBar, bottomBar)
    val adapter = ViewPagerAdapter(supportFragmentManager)
    adapter.addFragment(ProgramsFragment.getInstance(), "programs_tag")
    adapter.addFragment(DeliveriesFragment.getInstance(), "delivery_tag")
    adapter.addFragment(SupportFragment(), "empty_tag")
    tabsViewPager.offscreenPageLimit = 3
    tabsViewPager.adapter = adapter
    bottomBar.selectTabAtPosition(PROGRAMMS_TAB)
    bottomBar.setOnTabSelectListener { position ->
      when (position) {
        R.id.action_programms -> showTab("Программы", PROGRAMMS_TAB)
        R.id.action_delivery -> showTab("Заказы", CALENDAR_TAB)
        R.id.action_support -> showTab("Поддержка", SUPPORT_TAB)
      }
    }
    setSupportActionBar(toolbar)
    initDrawer(toolbar, this)
    initCartBadgeIcon()
  }

  override fun onResume() {
    super.onResume()
    updateCartBadgeCount(badgeCount, cartProgramsLocalStorage.getCartPrograms().size)
  }

  override fun initCartBadgeIcon() {
    updateCartBadgeCount(badgeCount, cartProgramsLocalStorage.getCartPrograms().size)
    cartView.setOnClickListener { Router.openCartScreen(this) }
  }

  private fun showTab(title: String, position: Int) {
    toolbar.title = title
    tabsViewPager.currentItem = position
    if (position == CALENDAR_TAB) {
      appBar.elevation = 0f
    } else {
      appBar.elevation = 4f
    }
  }

  fun initDrawer(toolbar: android.support.v7.widget.Toolbar, activity: Activity) {
    val itemPrograms = PrimaryDrawerItem().withName("Программы").withSelectable(false)
    val itemPlaces = PrimaryDrawerItem().withName("Места").withSelectable(false)
    val itemPaymentHistory = PrimaryDrawerItem().withName("История платежей").withSelectable(false)
    val itemSubstitutions = PrimaryDrawerItem().withName("Замены").withSelectable(false)
    val itemSpecial = PrimaryDrawerItem().withName("Акции").withSelectable(false)
    val itemSettings = SecondaryDrawerItem().withName("Настройки").withSelectable(false)

    val accountHeader: AccountHeader = AccountHeaderBuilder()
        .withActivity(this)
        .addProfiles(
            ProfileDrawerItem().withName(userProfileStorage.getUser().firstName).withEmail(
                userProfileStorage.getUser().email).withIcon(
                resources.getDrawable(R.drawable.ic_person_24))
        )
        .withHeaderBackground(R.color.colorPrimary)
        .withSelectionListEnabledForSingleProfile(false)
        .withProfileImagesClickable(false)
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
            itemSubstitutions,
            itemSpecial,
            DividerDrawerItem(),
            itemSettings
        )
        .withOnDrawerItemClickListener { view, position, drawerItem ->
          when (drawerItem) {
            itemSettings -> {
              Router.openSettingsScreen(this)
            }
            itemPlaces -> {
              Router.openPlacesScreen(this, 0)
            }
            itemPrograms -> {
              Router.openPurchasesScreen(this)
            }
            itemPaymentHistory -> {
              Router.openOrdersScreen(this)
            }
            itemSpecial -> {
              Router.openPromotionsScreen(this)
            }
            itemSubstitutions -> {
              Router.openSubsScreen(this)
            }

          }
          true
        }
        .build()
  }

  fun openSettingsFromProfile(): Boolean {
    Router.openSettingsScreen(this)
    return true
  }

}
