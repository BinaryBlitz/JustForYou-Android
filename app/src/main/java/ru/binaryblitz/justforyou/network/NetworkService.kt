package ru.binaryblitz.justforyou.network

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.binaryblitz.justforyou.data.programs.Program
import ru.binaryblitz.justforyou.data.user.UserInfo
import ru.binaryblitz.justforyou.network.models.UserData
import ru.binaryblitz.justforyou.network.responses.CreateTokenResponse
import ru.binaryblitz.justforyou.network.responses.VerifyTokenResponse

/**
 * A Service that performs network requests
 */
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

  fun createUser(user: UserData): Single<UserInfo> {
    return serviceApi.createUser(user)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .map { it }
  }

  fun getUser(token: String): Single<UserInfo> {
    return serviceApi.getUser(token)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .map { it }
  }

  fun getFoodPrograms(token: String): Single<List<Program>> {
    return serviceApi.getPrograms(token)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .map { it }
  }

}
