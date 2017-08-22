package ru.binaryblitz.justforyou.ui.main.settings

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import kotlinx.android.synthetic.main.activity_login.firstNameEdit
import kotlinx.android.synthetic.main.activity_settings.settingsContainer
import kotlinx.android.synthetic.main.activity_settings.toolbar
import kotlinx.android.synthetic.main.content_settings.aboutApp
import kotlinx.android.synthetic.main.content_settings.lastNameEdit
import kotlinx.android.synthetic.main.content_settings.nameEdit
import ru.binaryblitz.justforyou.R
import ru.binaryblitz.justforyou.R.string
import ru.binaryblitz.justforyou.data.user.UserInfo
import ru.binaryblitz.justforyou.data.user.UserProfileStorage
import ru.binaryblitz.justforyou.data.user.UserStorageImpl
import ru.binaryblitz.justforyou.di.JustForYouApp
import ru.binaryblitz.justforyou.network.NetworkService
import ru.binaryblitz.justforyou.network.models.User
import ru.binaryblitz.justforyou.network.models.UserData
import ru.binaryblitz.justforyou.ui.router.Router
import javax.inject.Inject


class SettingsActivity : AppCompatActivity(), TextWatcher {
  var userProfileStorage: UserProfileStorage = UserStorageImpl()
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
    nameEdit.addTextChangedListener(this)
    lastNameEdit.addTextChangedListener(this)
    aboutApp.setOnClickListener {
      Router.openJustForYouLink(this,
          getString(string.just_for_you_about_link))
    }
  }

  override fun afterTextChanged(p0: Editable?) {
    updateUser(userProfileStorage.getUser())
  }

  override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
  }

  override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
  }

  private fun updateUser(user: UserInfo) {
    val updatedUser = UserInfo(lastNameEdit.text.toString(), nameEdit.text.toString(), user.balance,
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
