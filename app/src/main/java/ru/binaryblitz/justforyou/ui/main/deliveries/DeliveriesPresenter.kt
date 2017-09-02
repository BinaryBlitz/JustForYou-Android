package ru.binaryblitz.justforyou.ui.main.deliveries

import com.arellomobile.mvp.InjectViewState
import ru.binaryblitz.justforyou.data.user.UserStorageImpl
import ru.binaryblitz.justforyou.di.JustForYouApp
import ru.binaryblitz.justforyou.network.NetworkService
import ru.binaryblitz.justforyou.ui.base.BasePresenter
import javax.inject.Inject

@InjectViewState
class DeliveriesPresenter : BasePresenter<DeliveriesView>() {
  @Inject
  lateinit var networkService: NetworkService
  @Inject
  lateinit var userProfileStorage: UserStorageImpl

  init {
    JustForYouApp.appComponent?.inject(this)
  }

  fun getDeliveries() {
    viewState.showProgress()
    networkService.getDeliveries(userProfileStorage.getToken())
        .subscribe(
            { deliveries ->
              viewState.hideProgress()
              viewState.showDeliveries(deliveries)
            },
            { errorResponse ->
              viewState.showError(errorResponse.localizedMessage)
              viewState.hideProgress()
            }
        )
  }
}
