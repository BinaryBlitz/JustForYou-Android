package ru.binaryblitz.justforyou.ui.main.user_orders

import com.arellomobile.mvp.InjectViewState
import ru.binaryblitz.justforyou.data.user.UserStorageImpl
import ru.binaryblitz.justforyou.di.JustForYouApp
import ru.binaryblitz.justforyou.network.NetworkService
import ru.binaryblitz.justforyou.ui.base.BasePresenter
import javax.inject.Inject

@InjectViewState
class UserOrdersPresenter : BasePresenter<UserOrdersView>() {
  @Inject
  lateinit var networkService: NetworkService
  @Inject
  lateinit var userProfileStorage: UserStorageImpl

  init {
    JustForYouApp.appComponent?.inject(this)
  }

  fun getOrders() {
    viewState.showProgress()
    networkService.getOrders(userProfileStorage.getToken())
        .subscribe(
            { orders ->
              viewState.hideProgress()
              viewState.showOrders(orders.sortedByDescending { it.id })
            },
            { errorResponse ->
              viewState.hideProgress()
              viewState.showError(errorResponse.localizedMessage)
            }
        )
  }

}
