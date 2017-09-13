package ru.binaryblitz.justforyou.ui.main.promotions

import com.arellomobile.mvp.InjectViewState
import ru.binaryblitz.justforyou.data.user.UserStorageImpl
import ru.binaryblitz.justforyou.di.JustForYouApp
import ru.binaryblitz.justforyou.network.NetworkService
import ru.binaryblitz.justforyou.ui.base.BasePresenter
import javax.inject.Inject

@InjectViewState
class PromotionsPresenter : BasePresenter<PromotionsView>() {
  @Inject
  lateinit var networkService: NetworkService
  @Inject
  lateinit var userProfileStorage: UserStorageImpl

  init {
    JustForYouApp.appComponent?.inject(this)
  }

  fun getPromotions() {
    viewState.showProgress()
    networkService.getPromotions(userProfileStorage.getToken())
        .subscribe(
            { promotions ->
              viewState.hideProgress()
              viewState.showPromotions(promotions)
            },
            { errorResponse ->
              viewState.hideProgress()
              viewState.showError(errorResponse.localizedMessage)
            }
        )
  }

}
