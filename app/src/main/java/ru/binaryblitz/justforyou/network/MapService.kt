package ru.binaryblitz.justforyou.network

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.binaryblitz.justforyou.network.responses.map.AddressesResponse

/**
 * A Service that performs network requests to google maps server
 */
private const val moscowCitySearchQuery: String = "Москва"

class MapService(private val serviceApi: MapsApiService) {

  fun getAddresses(lat: Double, lan: Double, apiToken: String): Single<AddressesResponse> {
    return serviceApi.getAddressFromLatLng("$lat ,$lan", apiToken, "ru")
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .map { it }
  }

  fun getAddresses(query: String, apiToken: String): Single<AddressesResponse> {
    return serviceApi.getLatLngFromQuery(query + moscowCitySearchQuery, apiToken, "ru")
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .map { it }
  }

}
