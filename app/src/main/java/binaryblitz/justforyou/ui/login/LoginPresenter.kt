package binaryblitz.justforyou.ui.login

import binaryblitz.justforyou.di.JustForYouApp
import binaryblitz.justforyou.network.NetworkService
import binaryblitz.justforyou.network.models.User
import binaryblitz.justforyou.network.models.UserData
import binaryblitz.justforyou.ui.base.BasePresenter
import com.arellomobile.mvp.InjectViewState
import javax.inject.Inject

@InjectViewState
class LoginPresenter : BasePresenter<LoginView>() {
  @Inject
  lateinit var service: NetworkService
  lateinit var token: String
  lateinit var phone: String

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

  fun checkPhoneCode(code: String){
    viewState.showProgress()
    service.verifyToken(token, phone, code)
        .subscribe(
            { (data) ->
              viewState.hideProgress()
              viewState.openUserView()
            },
            { errorResponse ->
              viewState.showError(errorResponse.localizedMessage)
              viewState.hideProgress()
            }
        )
  }

  fun createUser(lastName: String, firstName: String, email: String){
    viewState.showProgress()
    service.createUser(UserData(User(lastName, phone, firstName, email)))
        .subscribe(
            { (data) ->
              viewState.hideProgress()
              // TODO save user data
              viewState.successLogin()
            },
            { errorResponse ->
              viewState.showError(errorResponse.localizedMessage)
              viewState.hideProgress()
            }
        )
  }
}
