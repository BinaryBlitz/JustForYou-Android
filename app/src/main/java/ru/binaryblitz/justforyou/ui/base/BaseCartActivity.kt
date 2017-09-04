package ru.binaryblitz.justforyou.ui.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.annotation.Nullable
import android.view.View
import android.widget.TextView
import com.arellomobile.mvp.MvpAppCompatActivity
import ru.binaryblitz.justforyou.data.cart.ProgramsStorage
import ru.binaryblitz.justforyou.di.JustForYouApp
import javax.inject.Inject

abstract class BaseCartActivity : MvpAppCompatActivity() {
  @Inject
  lateinit var cartProgramsLocalStorage: ProgramsStorage

  init {
    JustForYouApp.appComponent?.inject(this)
  }

  override fun setContentView(layoutResID: Int) {
    super.setContentView(layoutResID)
  }

  override fun onCreate(@Nullable savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
  }

  // Set screen router to cart icon view container
  // and init views
  abstract fun initCartBadgeIcon()

  fun updateCartBadgeCount(cartBadgeTextView: TextView, count: Int) {
    runOnUiThread {
      if (count == 0) {
        cartBadgeTextView.visibility = View.INVISIBLE
      } else {
        cartBadgeTextView.visibility = View.VISIBLE
        cartBadgeTextView.text = "$count"
      }
    }
  }
}
