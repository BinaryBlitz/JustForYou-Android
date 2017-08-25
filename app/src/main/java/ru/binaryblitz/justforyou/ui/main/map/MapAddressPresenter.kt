package ru.binaryblitz.justforyou.ui.main.map

import com.arellomobile.mvp.InjectViewState
import com.google.android.gms.maps.model.LatLng
import ru.binaryblitz.justforyou.di.JustForYouApp
import ru.binaryblitz.justforyou.network.MapService
import ru.binaryblitz.justforyou.ui.base.BasePresenter
import javax.inject.Inject

@InjectViewState
class MapAddressPresenter : BasePresenter<MapAddressView>() {
  @Inject
  lateinit var networkService: MapService

  init {
    JustForYouApp.appComponent?.inject(this)
  }

  fun getAddressFromLocation(latLng: LatLng, apiToken: String) {
    viewState.showProgress()
    viewState.hideAddressInfo()
    networkService.getAddresses(latLng.latitude, latLng.longitude, apiToken)
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
    networkService.getAddresses(query, apiToken)
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


}
