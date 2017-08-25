package ru.binaryblitz.justforyou.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import ru.binaryblitz.justforyou.network.responses.map.AddressesResponse

/**
 * Google maps geocoder API methods
 */
interface MapsApiService {
  @GET("geocode/json")
  fun getAddressFromLatLng(@Query("latlng") lat: String,
      @Query("key") apiKey: String, @Query("language") language: String): Single<AddressesResponse>

  @GET("geocode/json")
  fun getLatLngFromQuery(@Query("address") lat: String,
      @Query("key") apiKey: String, @Query("language") language: String): Single<AddressesResponse>

}
