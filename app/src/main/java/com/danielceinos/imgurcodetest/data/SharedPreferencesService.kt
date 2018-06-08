package com.danielceinos.imgurcodetest.data

import android.content.SharedPreferences
import javax.inject.Inject

/**
 * Created by Daniel S on 08/06/2018.
 */
class SharedPreferencesService @Inject constructor(private val sharedPreferences: SharedPreferences) {

  fun savePref(key: String, value: String) {
    with(sharedPreferences.edit()) {
      putString(key, value)
      apply()
    }
  }
}