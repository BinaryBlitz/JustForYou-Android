package ru.binaryblitz.justforyou.ui.main.map

import com.arellomobile.mvp.InjectViewState
import com.google.android.gms.maps.model.LatLng
import ru.binaryblitz.justforyou.data.user.UserStorageImpl
import ru.binaryblitz.justforyou.di.JustForYouApp
import ru.binaryblitz.justforyou.network.MapService
import ru.binaryblitz.justforyou.network.NetworkService
import ru.binaryblitz.justforyou.network.responses.delivery_addresses.create.AddressBodyData
import ru.binaryblitz.justforyou.ui.base.BasePresenter
import javax.inject.Inject

@InjectViewState
class MapAddressPresenter : BasePresenter<MapAddressView>() {
  @Inject
  lateinit var mapService: MapService
  @Inject
  lateinit var networkService: NetworkService
  @Inject
  lateinit var userProfileStorage: UserStorageImpl

  init {
    JustForYouApp.appComponent?.inject(this)
  }

  fun getAddressFromLocation(latLng: LatLng, apiToken: String) {
    viewState.showProgress()
    viewState.hideAddressInfo()
    mapService.getAddresses(latLng.latitude, latLng.longitude, apiToken)
        .subscribe(
            { addressesResponse ->
              viewState.hideProgress()
              if (addressesResponse.results?.size!! > 0) {
                viewState.showAddress(addressesResponse.results.first()!!)
                viewState.showAddressInfo()
              } else {
                viewState.hideAddressInfo()
              }
            },
            { errorResponse ->
              viewState.showError(errorResponse.localizedMessage)
              viewState.hideProgress()
            }
        )
  }

  fun getLocationFromQuery(query: String, apiToken: String) {
    viewState.showProgress()
    viewState.hideAddressInfo()
    mapService.getAddresses(query, apiToken)
        .subscribe(
            { addressesResponse ->
              viewState.hideProgress()
              if (addressesResponse.results?.size!! > 0) {
                viewState.showLocation(addressesResponse.results.first()!!)
                viewState.showAddress(addressesResponse.results.first()!!)
                viewState.showAddressInfo()
              } else {
                viewState.hideAddressInfo()
              }
            },
            { errorResponse ->
              viewState.showError(errorResponse.localizedMessage)
              viewState.hideProgress()
            }
        )
  }

  fun addNewAddress(addressBodyData: AddressBodyData) {
    viewState.showProgress()
    networkService.createDeliveryAddress(addressBodyData, userProfileStorage.getToken())
        .subscribe(
            { addressesResponse ->
              viewState.hideProgress()
              viewState.successAddressAddition()
            },
            { errorResponse ->
              viewState.showError(errorResponse.localizedMessage)
              viewState.hideProgress()
            }
        )

  }


}
