package binaryblitz.justforyou.network

import binaryblitz.justforyou.network.responses.VerificationTokenResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class NetworkService(private val serviceApi: ApiService) {

  fun createToken(phoneNumber: String): Single<VerificationTokenResponse> {
    return serviceApi.createToken(phoneNumber)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .map { it }
  }

}
