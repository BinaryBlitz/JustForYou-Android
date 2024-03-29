package ru.binaryblitz.justforyou.data.user

import android.content.Context
import android.content.SharedPreferences
import ru.binaryblitz.justforyou.data.cart.ProgramsStorage
import ru.binaryblitz.justforyou.di.JustForYouApp


/**
 * An implementation of UserProfileStorage based on Android SharedPreferences
 */
private const val PROFILE_PREFERENCES_NAME = "profile_preferences"
private const val FIELD_LASTNAME = "lastname"
private const val FIELD_FIRSTNAME = "firstname"
private const val FIELD_BALANCE = "balance"
private const val FIELD_PHONE = "phone"
private const val FIELD_ID = "id"
private const val FIELD_EMAIL = "email"
private const val FIELD_TOKEN = "token"
private const val FIELD_DEVICE_TOKEN = "device_token"
private const val FIELD_NOTIFICATIONS = "device_notifications"

class UserStorageImpl : UserProfileStorage {
  var context: Context = JustForYouApp.appComponent!!.context

  override fun getUser(): UserInfo {
    val preferences = getProfileSharedPreferences()
    return UserInfo(getLastName(preferences),
        getFirstName(preferences), getBalance(preferences),
        getPhone(preferences), getId(preferences), getEmail(preferences), getToken(preferences))
  }

  override fun saveUser(user: UserInfo) {
    val preferences = getProfileSharedPreferences()
    val editor = preferences.edit()
    editor.putString(FIELD_LASTNAME, user.lastName)
    editor.putString(FIELD_FIRSTNAME, user.firstName)
    editor.putInt(FIELD_BALANCE, user.balance)
    editor.putString(FIELD_PHONE, user.phoneNumber)
    editor.putInt(FIELD_ID, user.id)
    editor.putString(FIELD_EMAIL, user.email)
    editor.putString(FIELD_TOKEN, user.apiToken)

    editor.apply()
  }

  override fun saveDeviceToken(token: String) {
    val preferences = getProfileSharedPreferences()
    val editor = preferences.edit()
    editor.putString(FIELD_DEVICE_TOKEN, token)
    editor.apply()
  }

  override fun setPushNotificationsEnabled(enabled: Boolean) {
    val preferences = getProfileSharedPreferences()
    val editor = preferences.edit()
    editor.putBoolean(FIELD_NOTIFICATIONS, enabled)
    editor.apply()
  }

  override fun isNotificationsEnabled(): Boolean {
    val preferences = getProfileSharedPreferences()
    return getNotifications(preferences)
  }

  override fun getDeviceToken(): String {
    val preferences = getProfileSharedPreferences()
    return getDeviceToken(preferences)
  }

  override fun getToken(): String {
    val preferences = getProfileSharedPreferences()
    return getToken(preferences)
  }

  private fun getProfileSharedPreferences(): SharedPreferences {
    return context.getSharedPreferences(PROFILE_PREFERENCES_NAME, Context.MODE_PRIVATE)
  }

  private fun getToken(preferences: SharedPreferences): String {
    val tokenField = preferences.getString(FIELD_TOKEN, "")
    return tokenField
  }

  private fun getDeviceToken(preferences: SharedPreferences): String {
    val tokenField = preferences.getString(FIELD_DEVICE_TOKEN, "")
    return tokenField
  }

  private fun getNotifications(preferences: SharedPreferences): Boolean {
    val isEnabled = preferences.getBoolean(FIELD_NOTIFICATIONS, true)
    return isEnabled
  }

  private fun getLastName(preferences: SharedPreferences): String {
    val lastName = preferences.getString(FIELD_LASTNAME, "")
    return lastName
  }

  private fun getFirstName(preferences: SharedPreferences): String {
    val firstName = preferences.getString(FIELD_FIRSTNAME, "")
    return firstName
  }

  private fun getBalance(preferences: SharedPreferences): Int {
    val balance = preferences.getInt(FIELD_BALANCE, 0)
    return balance
  }

  private fun getPhone(preferences: SharedPreferences): String {
    val phone = preferences.getString(FIELD_PHONE, "")
    return phone
  }

  private fun getId(preferences: SharedPreferences): Int {
    val id = preferences.getInt(FIELD_ID, 0)
    return id
  }

  private fun getEmail(preferences: SharedPreferences): String {
    val email = preferences.getString(FIELD_EMAIL, "")
    return email
  }

  fun logoutUser() {
    val preferences = getProfileSharedPreferences()
    preferences.edit().clear().apply()
    val cartStorage: ProgramsStorage = ProgramsStorage()
    cartStorage.clear()
  }


}
