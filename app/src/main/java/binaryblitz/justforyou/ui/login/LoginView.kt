package binaryblitz.justforyou.ui.login

import binaryblitz.justforyou.ui.base.BaseLCEView
import com.arellomobile.mvp.MvpView

/**
 * Created by ilyasavin on 8/9/17.
 */
interface LoginView : MvpView, BaseLCEView {
  fun showPhoneForm()
  fun activateVerificationView(number: String)
  fun openUserView()
  fun successLogin()
}