package ru.binaryblitz.justforyou.data.user

interface UserProfileStorage {
  fun getUser(): UserInfo
  fun saveUser(user: UserInfo)
  fun getToken(): String
}
