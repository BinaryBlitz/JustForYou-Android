package ru.binaryblitz.justforyou.ui.main.settings

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.widget.CompoundButton
import kotlinx.android.synthetic.main.activity_login.firstNameEdit
import kotlinx.android.synthetic.main.activity_settings.settingsContainer
import kotlinx.android.synthetic.main.activity_settings.toolbar
import kotlinx.android.synthetic.main.content_settings.aboutApp
import kotlinx.android.synthetic.main.content_settings.lastNameEdit
import kotlinx.android.synthetic.main.content_settings.logoutButton
import kotlinx.android.synthetic.main.content_settings.myPaymentCards
import kotlinx.android.synthetic.main.content_settings.notificationsSwitch
import ru.binaryblitz.justforyou.R
import ru.binaryblitz.justforyou.R.string
import ru.binaryblitz.justforyou.data.user.UserInfo
import ru.binaryblitz.justforyou.data.user.UserStorageImpl
import ru.binaryblitz.justforyou.di.JustForYouApp
import ru.binaryblitz.justforyou.network.NetworkService
import ru.binaryblitz.justforyou.network.models.User
import ru.binaryblitz.justforyou.network.models.UserData
import ru.binaryblitz.justforyou.network.models.token.TokenData
import ru.binaryblitz.justforyou.network.models.token.UserToken
import ru.binaryblitz.justforyou.ui.router.Router
import javax.inject.Inject


class SettingsActivity : AppCompatActivity(), TextWatcher {
  @Inject
  lateinit var userProfileStorage: UserStorageImpl
  @Inject
  lateinit var networkService: NetworkService

  init {
    JustForYouApp.appComponent?.inject(this)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_settings)
    initViewElements()
  }

  private fun initViewElements() {
    setSupportActionBar(toolbar)
    toolbar.title = getString(string.settings)
    toolbar.setNavigationIcon(R.drawable.ic_arrow_back24b)
    toolbar.setNavigationOnClickListener { onBackPressed() }
    firstNameEdit.setText(userProfileStorage.getUser().firstName)
    lastNameEdit.setText(userProfileStorage.getUser().lastName)
    firstNameEdit.addTextChangedListener(this)
    lastNameEdit.addTextChangedListener(this)
    aboutApp.setOnClickListener {
      Router.openJustForYouLink(this,
          getString(string.just_for_you_about_link))
    }
    myPaymentCards.setOnClickListener { Router.openPaymentCardsScreen(this) }
    logoutButton.setOnClickListener {
      userProfileStorage.logoutUser()
      Router.openMainScreenAfterLogout(this)
    }

    notificationsSwitch.isChecked = userProfileStorage.isNotificationsEnabled()
    notificationsSwitch.setOnCheckedChangeListener(
        CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
          if (isChecked) {
            networkService.sendDeviceToken(TokenData(
                UserToken(userProfileStorage.getDeviceToken(), "android")),
                userProfileStorage.getToken())
                .subscribe({ user -> }, { errorResponse -> })
          } else {
            networkService.sendDeviceToken(TokenData(
                UserToken("", "")),
                userProfileStorage.getToken())
                .subscribe({ user -> }, { errorResponse -> })
          }
          userProfileStorage.setPushNotificationsEnabled(isChecked)
        })
  }

  override fun afterTextChanged(p0: Editable?) {
    updateUser(userProfileStorage.getUser())
  }

  override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
  }

  override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
  }

  private fun updateUser(user: UserInfo) {
    val updatedUser = UserInfo(lastNameEdit.text.toString(), firstNameEdit.text.toString(),
        user.balance,
        user.phoneNumber, user.id, user.email, user.apiToken)
    networkService.updateUser(UserData(User(updatedUser.lastName,
        null, updatedUser.firstName, null)), userProfileStorage.getToken())
        .subscribe(
            { response ->
              userProfileStorage.saveUser(updatedUser)
              Snackbar.make(settingsContainer, getString(string.success_update_user),
                  Snackbar.LENGTH_SHORT).show()
            },
            { errorResponse ->
              userProfileStorage.saveUser(updatedUser)
              Snackbar.make(settingsContainer, getString(string.success_update_user),
                  Snackbar.LENGTH_SHORT).show()
            }
        )

  }

}
