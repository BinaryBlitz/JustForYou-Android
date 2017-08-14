package ru.binaryblitz.justforyou.ui.login

import com.arellomobile.mvp.MvpView
import ru.binaryblitz.justforyou.ui.base.BaseLCEView

interface LoginView : MvpView, BaseLCEView {
  fun showPhoneForm()
  fun activateVerificationView(number: String)
  fun openUserView()
  fun successLogin()
}