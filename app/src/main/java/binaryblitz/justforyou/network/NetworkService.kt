package binaryblitz.justforyou.network

import binaryblitz.justforyou.network.models.UserData
import binaryblitz.justforyou.network.responses.CreateTokenResponse
import binaryblitz.justforyou.network.responses.UserResponse
import binaryblitz.justforyou.network.responses.VerifyTokenResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class NetworkService(private val serviceApi: ApiService) {

  fun createToken(phoneNumber: String): Single<CreateTokenResponse> {
    return serviceApi.createToken(phoneNumber)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .map { it }
  }

  fun verifyToken(token: String, phoneNumber: String, code: String): Single<VerifyTokenResponse> {
    return serviceApi.verifyToken(token, phoneNumber, code)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .map { it }
  }

  fun createUser(user: UserData): Single<UserResponse> {
    return serviceApi.createUser(user)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .map { it }
  }

}
