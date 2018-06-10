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

  fun getRefreshToken() = sharedPreferencesService.getRefreshToken()

  private fun saveOauthToken(it: Response<OauthToken>?) {
    it?.body()?.getAccessToken()?.let { sharedPreferencesService.saveToken(it) }
    it?.body()?.getAccountUsername()?.let { sharedPreferencesService.saveAccountUsername(it) }
    it?.body()?.getRefreshToken()?.let { sharedPreferencesService.saveRefreshToken(it) }
  }
}