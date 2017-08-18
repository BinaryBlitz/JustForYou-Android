package ru.binaryblitz.justforyou.ui.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.annotation.Nullable
import android.view.Menu
import android.view.MenuItem
import com.arellomobile.mvp.MvpAppCompatActivity
import ru.binaryblitz.justforyou.R
import ru.binaryblitz.justforyou.data.cart.CartLocalStorage
import ru.binaryblitz.justforyou.data.cart.ProgramsStorage
import ru.binaryblitz.justforyou.ui.router.Router

open class BaseActivity : MvpAppCompatActivity() {
  var cartProgramsLocalStorage: CartLocalStorage = ProgramsStorage()

  override fun setContentView(layoutResID: Int) {
    super.setContentView(layoutResID)
  }

  open override fun onCreate(@Nullable savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_cart, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    val id = item.getItemId()

    if (id == R.id.action_cart) {
      Router.openCartScreen(this)
      return true
    }

    return super.onOptionsItemSelected(item)
  }
}
