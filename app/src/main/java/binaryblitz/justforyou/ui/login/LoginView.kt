package binaryblitz.justforyou.ui.login

import binaryblitz.justforyou.ui.base.BaseLCEView
import com.arellomobile.mvp.MvpView

interface LoginView : MvpView, BaseLCEView {
  fun showPhoneForm()
  fun activateVerificationView(number: String)
  fun openUserView()
  fun successLogin()
}