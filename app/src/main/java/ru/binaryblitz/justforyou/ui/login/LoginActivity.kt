package ru.binaryblitz.justforyou.ui.login

import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.transition.ChangeBounds
import android.transition.ChangeImageTransform
import android.transition.TransitionManager
import android.transition.TransitionSet
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.widget.ImageView.ScaleType.FIT_XY
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
import kotlinx.android.synthetic.main.activity_login.loginCoordinatorView
import kotlinx.android.synthetic.main.activity_login.logoView
import kotlinx.android.synthetic.main.activity_login.numberTextMessage
import kotlinx.android.synthetic.main.activity_login.phoneNumberEdit
import kotlinx.android.synthetic.main.activity_login.progressBar
import kotlinx.android.synthetic.main.activity_login.statusText
import kotlinx.android.synthetic.main.activity_login.userContainerView
import kotlinx.android.synthetic.main.activity_program.toolbar
import ru.binaryblitz.justforyou.R
import ru.binaryblitz.justforyou.R.string
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
    toolbar.setNavigationOnClickListener { onBackPressed() }

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

    val handler = Handler()
    handler.postDelayed(object : Runnable {
      override fun run() {
        animLogo()
      }
    }, 1000)
  }

  private fun animLogo() {
    TransitionManager.beginDelayedTransition(loginCoordinatorView, TransitionSet()
        .addTransition(ChangeBounds())
        .addTransition(ChangeImageTransform()))

    val params: LayoutParams = logoView.layoutParams
    val factor = resources.displayMetrics.density
    params.width = (120 * factor).toInt()
    params.height = (120 * factor).toInt()

    logoView.layoutParams = params
    logoView.scaleType = FIT_XY
  }

  override fun showUserError() {
    Snackbar.make(loginCoordinatorView, getString(string.user_creation_error),
        Snackbar.LENGTH_SHORT).show()
  }

  override fun showCodeError() {
    Snackbar.make(loginCoordinatorView, getString(R.string.wrong_code),
        Snackbar.LENGTH_SHORT).show()
  }

  override fun showNumberError() {
    Snackbar.make(loginCoordinatorView, getString(string.incorrect_number),
        Snackbar.LENGTH_SHORT).show()
  }

  override fun showProgress() {
    progressBar.visibility = View.VISIBLE
  }

  override fun hideProgress() {
    progressBar.visibility = View.GONE
  }

  override fun showError(message: String) {
    Toast.makeText(this, getString(R.string.user_creation_error), Toast.LENGTH_SHORT).show()
  }

  override fun showPhoneForm() {
    numberTextMessage.text = getString(string.enter_number)
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
    numberTextMessage.text = getString(string.enter_code)
    toolbar.setNavigationIcon(R.drawable.ic_arrow_back24b)
    toolbar.setNavigationOnClickListener { showPhoneForm() }
    statusText.text = getString(R.string.code_send_to) + " " + phone
    phoneNumberEdit.visibility = View.GONE
    codeVerificationEdit.visibility = View.VISIBLE
  }
}
