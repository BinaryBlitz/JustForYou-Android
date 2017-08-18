package ru.binaryblitz.justforyou.ui.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.annotation.Nullable
import android.view.View
import android.widget.TextView
import com.arellomobile.mvp.MvpAppCompatActivity
import ru.binaryblitz.justforyou.data.cart.CartLocalStorage
import ru.binaryblitz.justforyou.data.cart.ProgramsStorage

abstract class BaseActivity : MvpAppCompatActivity() {
  var cartProgramsLocalStorage: CartLocalStorage = ProgramsStorage()

  override fun setContentView(layoutResID: Int) {
    super.setContentView(layoutResID)
  }

  open override fun onCreate(@Nullable savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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
