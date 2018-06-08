package com.danielceinos.imgurcodetest.data.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


/**
 * Created by Daniel S on 08/06/2018.
 */
data class TokenRequest(

    @SerializedName("client_id")
    @Expose
    val clientId: String,

    @SerializedName("client_secret")
    @Expose
    val clientSecret: String,

    @SerializedName("grant_type")
    @Expose
    val grantType: String,

    @SerializedName("code")
    @Expose
    val code: String

)