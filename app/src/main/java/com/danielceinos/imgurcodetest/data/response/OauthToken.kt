package com.danielceinos.imgurcodetest.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



/**
 * Created by Daniel S on 08/06/2018.
 */
class OauthToken {
  @SerializedName("access_token")
  @Expose
  private var accessToken: String? = null
  @SerializedName("expires_in")
  @Expose
  private var expiresIn: Int? = null
  @SerializedName("token_type")
  @Expose
  private var tokenType: String? = null
  @SerializedName("scope")
  @Expose
  private var scope: Any? = null
  @SerializedName("refresh_token")
  @Expose
  private var refreshToken: String? = null
  @SerializedName("account_id")
  @Expose
  private var accountId: Int? = null
  @SerializedName("account_username")
  @Expose
  private var accountUsername: String? = null

  fun getAccessToken(): String? {
    return accessToken
  }

  fun setAccessToken(accessToken: String) {
    this.accessToken = accessToken
  }

  fun getExpiresIn(): Int? {
    return expiresIn
  }

  fun setExpiresIn(expiresIn: Int?) {
    this.expiresIn = expiresIn
  }

  fun getTokenType(): String? {
    return tokenType
  }

  fun setTokenType(tokenType: String) {
    this.tokenType = tokenType
  }

  fun getScope(): Any? {
    return scope
  }

  fun setScope(scope: Any) {
    this.scope = scope
  }

  fun getRefreshToken(): String? {
    return refreshToken
  }

  fun setRefreshToken(refreshToken: String) {
    this.refreshToken = refreshToken
  }

  fun getAccountId(): Int? {
    return accountId
  }

  fun setAccountId(accountId: Int?) {
    this.accountId = accountId
  }

  fun getAccountUsername(): String? {
    return accountUsername
  }

  fun setAccountUsername(accountUsername: String) {
    this.accountUsername = accountUsername
  }
}