package com.danielceinos.imgurcodetest.data

import com.danielceinos.imgurcodetest.data.request.RefreshTokenRequest
import com.danielceinos.imgurcodetest.data.request.TokenRequest
import com.danielceinos.imgurcodetest.data.response.OauthToken
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by Daniel S on 08/06/2018.
 */
class AuthRepository @Inject constructor(private val imgurService: ImgurService, private val sharedPreferencesService: SharedPreferencesService) {

  fun doAuth(tokenRequest: TokenRequest) = imgurService.getToken(tokenRequest)
      .doOnSuccess {
        saveOauthToken(it)
      }

  fun doRefreshToken(refreshTokenRequest: RefreshTokenRequest) = imgurService.refreshToken(refreshTokenRequest)
      .doOnSuccess {
        saveOauthToken(it)
      }

  fun getRefreshToken() = sharedPreferencesService.getPref("refresh_token")

  fun getAccountUsername() = sharedPreferencesService.getPref("account_username")

  private fun saveOauthToken(it: Response<OauthToken>?) {
    it?.body()?.getAccessToken()?.let { sharedPreferencesService.savePref("token", it) }
    it?.body()?.getAccountUsername()?.let { sharedPreferencesService.savePref("account_username", it) }
    it?.body()?.getRefreshToken()?.let { sharedPreferencesService.savePref("refresh_token", it) }
  }
}