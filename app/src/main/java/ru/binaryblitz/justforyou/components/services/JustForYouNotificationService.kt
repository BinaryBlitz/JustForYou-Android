package ru.binaryblitz.justforyou.components.services

import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService
import ru.binaryblitz.justforyou.data.user.UserStorageImpl

class JustForYouNotificationService : FirebaseInstanceIdService() {

  override fun onTokenRefresh() {
    val refreshedToken = FirebaseInstanceId.getInstance().token
    saveToken(refreshedToken!!)
  }

  private fun saveToken(token: String) {
    var storage: UserStorageImpl = UserStorageImpl()
    storage.saveDeviceToken(token)
  }

  companion object {
    private val TAG = "FirebaseIIDService"
  }
}
