package ru.binaryblitz.justforyou.ui.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.redmadrobot.inputmask.MaskedTextChangedListener
import kotlinx.android.synthetic.main.activity_login.codeVerificationEdit
import kotlinx.android.synthetic.main.activity_login.emailEdit
import kotlinx.android.synthetic.main.activity_login.finishLoginButton
import kotlinx.android.synthetic.main.activity_login.firstNameEdit
import kotlinx.android.synthetic.main.activity_login.lastNameEdit
import kotlinx.android.synthetic.main.activity_login.loginContainerView
import kotlinx.android.synthetic.main.activity_login.phoneNumberEdit
import kotlinx.android.synthetic.main.activity_login.progressBar
import kotlinx.android.synthetic.main.activity_login.statusText
import kotlinx.android.synthetic.main.activity_login.toolbar
import kotlinx.android.synthetic.main.activity_login.userContainerView
import ru.binaryblitz.justforyou.R
import ru.binaryblitz.justforyou.ui.router.Router


class LoginActivity : MvpAppCompatActivity(), LoginView {
  @InjectPresenter
  lateinit var loginPresenter: LoginPresenter
  private val phoneNumberMask = "+7 ([000]) [000] [00] [00]"
  private val verificationCodeMask = "[0000]"

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)
    initViewElements()
  }

  private fun initViewElements() {
    toolbar.title = getString(R.string.login_text)

    val phoneListener = MaskedTextChangedListener(phoneNumberMask, false,
        phoneNumberEdit, null,
        object : MaskedTextChangedListener.ValueListener {
          override fun onTextChanged(maskFilled: Boolean, extractedValue: String) {
            if (maskFilled)
              loginPresenter.createToken(extractedValue)
          }
        }
    )

    val codeListener = MaskedTextChangedListener(verificationCodeMask, false,
        codeVerificationEdit, null,
        object : MaskedTextChangedListener.ValueListener {
          override fun onTextChanged(maskFilled: Boolean, extractedValue: String) {
            if (maskFilled)
              loginPresenter.checkPhoneCode(extractedValue)
          }
        }
    )

    phoneNumberEdit.addTextChangedListener(phoneListener)
    phoneNumberEdit.onFocusChangeListener = phoneListener
    phoneNumberEdit.hint = phoneListener.placeholder()

    codeVerificationEdit.addTextChangedListener(codeListener)
    codeVerificationEdit.onFocusChangeListener = codeListener

    finishLoginButton.setOnClickListener {
      loginPresenter.createUser(lastNameEdit.text.toString(), firstNameEdit.text.toString(),
          emailEdit.text.toString())
    }
  }

  override fun showProgress() {
    progressBar.visibility = View.VISIBLE
  }

  override fun hideProgress() {
    progressBar.visibility = View.GONE
  }

  override fun showError(message: String) {
    Toast.makeText(this, getString(R.string.wrong_code), Toast.LENGTH_SHORT).show()
  }

  override fun showPhoneForm() {
    toolbar.navigationIcon = null
    statusText.text = getString(R.string.login_message)
    phoneNumberEdit.setText("")
    phoneNumberEdit.visibility = View.VISIBLE
    codeVerificationEdit.visibility = View.GONE
  }

  override fun successLogin() {
    Router.openOnboardingScreen(this)
  }

  override fun openUserView() {
    statusText.text = getString(R.string.about_user)
    loginContainerView.visibility = View.GONE
    toolbar.navigationIcon = null
    userContainerView.visibility = View.VISIBLE
  }

  override fun activateVerificationView(phone: String) {
    toolbar.setNavigationIcon(R.drawable.ic_arrow_back24w)
    toolbar.setNavigationOnClickListener { showPhoneForm() }
    statusText.text = getString(R.string.code_send_to) + phone
    phoneNumberEdit.visibility = View.GONE
    codeVerificationEdit.visibility = View.VISIBLE
  }
}
