package ru.binaryblitz.justforyou.ui.main.delivery_addresses

import com.arellomobile.mvp.InjectViewState
import ru.binaryblitz.justforyou.data.user.UserStorageImpl
import ru.binaryblitz.justforyou.di.JustForYouApp
import ru.binaryblitz.justforyou.network.NetworkService
import ru.binaryblitz.justforyou.ui.base.BasePresenter
import javax.inject.Inject

@InjectViewState
class PlacesPresenter : BasePresenter<PlacesView>() {
  @Inject
  lateinit var networkService: NetworkService
  @Inject
  lateinit var userProfileStorage: UserStorageImpl

  init {
    JustForYouApp.appComponent?.inject(this)
  }

  fun getPlaces() {
    viewState.showProgress()
    networkService.getDeliveryAddresses(userProfileStorage.getToken())
        .subscribe(
            { places ->
              viewState.hideProgress()
              viewState.showPlaces(places)
            },
            { errorResponse ->
              viewState.hideProgress()
              viewState.showError(errorResponse.localizedMessage)
            }
        )
  }

  fun removeAddress(addressId: Int) {
    viewState.showProgress()
    networkService.removeAddress(addressId, userProfileStorage.getToken())
        .subscribe(
            { response ->
              viewState.hideProgress()
              getPlaces()
            },
            { errorResponse ->
              viewState.hideProgress()
              getPlaces()
            }
        )
  }

}
