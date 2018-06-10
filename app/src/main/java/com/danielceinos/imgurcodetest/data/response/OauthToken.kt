package com.danielceinos.imgurcodetest.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


/**
 * Created by Daniel S on 08/06/2018.
 */
data class OauthToken(
    @SerializedName("access_token")
    @Expose
    var accessToken: String? = null,
    @SerializedName("expires_in")
    @Expose
    var expiresIn: Int? = null,
    @SerializedName("token_type")
    @Expose
    var tokenType: String? = null,
    @SerializedName("scope")
    @Expose
    var scope: Any? = null,
    @SerializedName("refresh_token")
    @Expose
    var refreshToken: String? = null,
    @SerializedName("account_id")
    @Expose
    var accountId: Int? = null,
    @SerializedName("account_username")
    @Expose
    var accountUsername: String? = null)