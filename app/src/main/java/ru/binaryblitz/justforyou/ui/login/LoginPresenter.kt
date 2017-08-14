package ru.binaryblitz.justforyou.ui.login

import com.arellomobile.mvp.InjectViewState
import ru.binaryblitz.justforyou.data.user.UserProfileStorage
import ru.binaryblitz.justforyou.data.user.UserStorageImpl
import ru.binaryblitz.justforyou.di.JustForYouApp
import ru.binaryblitz.justforyou.network.NetworkService
import ru.binaryblitz.justforyou.network.models.User
import ru.binaryblitz.justforyou.network.models.UserData
import ru.binaryblitz.justforyou.ui.base.BasePresenter
import javax.inject.Inject

@InjectViewState
class LoginPresenter : BasePresenter<LoginView>() {
  @Inject
  lateinit var service: NetworkService
  lateinit var token: String
  lateinit var phone: String
  private var userProfileStorage: UserProfileStorage = UserStorageImpl()

  init {
    JustForYouApp.appComponent?.inject(this)
  }

  fun createToken(number: String) {
    viewState.showProgress()
    service.createToken("+7" + number)
        .subscribe(
            { response ->
              token = response.token!!
              phone = response.phoneNumber!!
              viewState.hideProgress()
              viewState.activateVerificationView(phone)

            },
            { errorResponse ->
              viewState.showError(errorResponse.localizedMessage)
              viewState.hideProgress()
            }
        )
  }

  fun checkPhoneCode(code: String) {
    viewState.showProgress()
    service.verifyToken(token, phone, code)
        .subscribe(
            { response ->
              viewState.hideProgress()
              if (response.apiToken != null) {
                getUser(response.apiToken)
              } else {
                viewState.openUserView()
              }
            },
            { errorResponse ->
              viewState.showError(errorResponse.localizedMessage)
              viewState.hideProgress()
            }
        )
  }

  fun createUser(lastName: String, firstName: String, email: String) {
    viewState.showProgress()
    service.createUser(UserData(User(lastName, phone, firstName, email)))
        .subscribe(
            { user ->
              viewState.hideProgress()
              userProfileStorage.saveUser(user)
              viewState.successLogin()
            },
            { errorResponse ->
              viewState.showError(errorResponse.localizedMessage)
              viewState.hideProgress()
            }
        )
  }

  fun getUser(token: String) {
    viewState.showProgress()
    service.getUser(token)
        .subscribe(
            { user ->
              viewState.hideProgress()
              user.apiToken = token
              userProfileStorage.saveUser(user)
              viewState.successLogin()
            },
            { errorResponse ->
              viewState.showError(errorResponse.localizedMessage)
              viewState.hideProgress()
            }
        )
  }
}
