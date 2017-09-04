package ru.binaryblitz.justforyou.data.user

interface UserProfileStorage {
  fun getUser(): UserInfo
  fun saveUser(user: UserInfo)
  fun getToken(): String
  fun saveDeviceToken(token: String)
  fun getDeviceToken(): String
  fun setPushNotificationsEnabled(enabled: Boolean)
  fun isNotificationsEnabled(): Boolean
}
