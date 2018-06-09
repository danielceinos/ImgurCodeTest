package com.danielceinos.imgurcodetest.data

import com.danielceinos.imgurcodetest.data.request.TokenRequest
import javax.inject.Inject

/**
 * Created by Daniel S on 08/06/2018.
 */
class AuthRepository @Inject constructor(private val imgurService: ImgurService, private val sharedPreferencesService: SharedPreferencesService) {

  fun doAuth(tokenRequest: TokenRequest) = imgurService.getToken(tokenRequest)
      .doOnSuccess {
        it?.body()?.getAccessToken()?.let { sharedPreferencesService.savePref("token", it) }
        it?.body()?.getAccountUsername()?.let { sharedPreferencesService.savePref("account_username", it) }
      }

  fun getToken() = sharedPreferencesService.getPref("token")

  fun getAccountUsername() = sharedPreferencesService.getPref("account_username")
}