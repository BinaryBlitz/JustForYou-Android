package binaryblitz.justforyou.ui.main

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import binaryblitz.justforyou.R
import binaryblitz.justforyou.data.user.UserProfileStorage
import binaryblitz.justforyou.data.user.UserStorageImpl
import binaryblitz.justforyou.ui.router.ScreenRouter
import kotlinx.android.synthetic.main.activity_main.message
import kotlinx.android.synthetic.main.activity_main.navigation

/**
 * The main activity of app that controls the transitions between tabs from bottom bar
 */
class MainActivity : AppCompatActivity() {

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
    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
  }

  private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
    when (item.itemId) {
      R.id.navigation_home -> {
        message.setText(R.string.title_home)
        return@OnNavigationItemSelectedListener true
      }
      R.id.navigation_dashboard -> {
        message.setText(R.string.title_dashboard)
        return@OnNavigationItemSelectedListener true
      }
      R.id.navigation_notifications -> {
        message.setText(R.string.title_notifications)
        return@OnNavigationItemSelectedListener true
      }
    }
    false
  }
}
