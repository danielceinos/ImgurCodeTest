package com.danielceinos.imgurcodetest.data

import android.content.SharedPreferences
import javax.inject.Inject

/**
 * Created by Daniel S on 08/06/2018.
 */
class SharedPreferencesService @Inject constructor(private val sharedPreferences: SharedPreferences) {

  val TOKEN_KEY = "TOKEN"
  val REFRESH_TOKEN_KEY = "REFRESH_TOKEN"
  val ACCOUNT_USERNAME_KEY = "ACCOUNT_USERNAME"

  fun getToken(): String? = getPref(TOKEN_KEY)
  fun getRefreshToken(): String? = getPref(REFRESH_TOKEN_KEY)
  fun getAccountUsername(): String? = getPref(ACCOUNT_USERNAME_KEY)

  fun saveToken(token: String) {
    savePref(TOKEN_KEY, token)
  }

  fun saveRefreshToken(refreshToken: String) {
    savePref(REFRESH_TOKEN_KEY, refreshToken)
  }

  fun saveAccountUsername(username: String) {
    savePref(ACCOUNT_USERNAME_KEY, username)
  }

  private fun savePref(key: String, value: String) {
    with(sharedPreferences.edit()) {
      putString(key, value)
      apply()
    }
  }

  private fun getPref(key: String) = sharedPreferences.getString(key, null)

}