package ru.binaryblitz.justforyou.ui.main.purchases

import com.arellomobile.mvp.InjectViewState
import ru.binaryblitz.justforyou.data.user.UserStorageImpl
import ru.binaryblitz.justforyou.di.JustForYouApp
import ru.binaryblitz.justforyou.network.NetworkService
import ru.binaryblitz.justforyou.ui.base.BasePresenter
import javax.inject.Inject

@InjectViewState
class PurchasesPresenter : BasePresenter<PurchasesView>() {
  @Inject
  lateinit var networkService: NetworkService
  @Inject
  lateinit var userProfileStorage: UserStorageImpl

  init {
    JustForYouApp.appComponent?.inject(this)
  }

  fun getPurchases() {
    viewState.showProgress()
    networkService.getPurchases(userProfileStorage.getToken())
        .subscribe(
            { purchases ->
              viewState.hideProgress()
              viewState.showPurchases(purchases)
            },
            { errorResponse ->
              viewState.hideProgress()
              viewState.showError(errorResponse.localizedMessage)
            }
        )
  }

}
