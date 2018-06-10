package com.danielceinos.imgurcodetest.data.request

import com.danielceinos.imgurcodetest.BuildConfig
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


/**
 * Created by Daniel S on 08/06/2018.
 */
data class RefreshTokenRequest(
    @SerializedName("refresh_token")
    @Expose
    val refreshToken: String,

    @SerializedName("client_id")
    @Expose
    val clientId: String = BuildConfig.clientId,

    @SerializedName("client_secret")
    @Expose
    val clientSecret: String = BuildConfig.clientSecret,

    @SerializedName("grant_type")
    @Expose
    val grantType: String = "refresh_token"

)