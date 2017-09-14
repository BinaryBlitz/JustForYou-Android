package ru.binaryblitz.justforyou.ui.main.substitutions

import com.arellomobile.mvp.InjectViewState
import ru.binaryblitz.justforyou.data.user.UserStorageImpl
import ru.binaryblitz.justforyou.di.JustForYouApp
import ru.binaryblitz.justforyou.network.NetworkService
import ru.binaryblitz.justforyou.ui.base.BasePresenter
import javax.inject.Inject

@InjectViewState
class SubstitutionsPresenter : BasePresenter<SubstitutionsView>() {
  @Inject
  lateinit var networkService: NetworkService
  @Inject
  lateinit var userProfileStorage: UserStorageImpl

  init {
    JustForYouApp.appComponent?.inject(this)
  }

  fun getSubstitutions() {
    viewState.showProgress()
    networkService.getUserSubstitutions(userProfileStorage.getToken())
        .subscribe(
            { subs ->
              viewState.hideProgress()
              viewState.showSubs(subs)
            },
            { errorResponse ->
              viewState.hideProgress()
              viewState.showError(errorResponse.localizedMessage)
            }
        )
  }

  fun removeSub(id:Int) {
    viewState.showProgress()
    networkService.removeUserSubstitution(id, userProfileStorage.getToken())
        .subscribe(
            { subs ->
              getSubstitutions()
            },
            { errorResponse ->
              getSubstitutions()
            }
        )
  }

}
