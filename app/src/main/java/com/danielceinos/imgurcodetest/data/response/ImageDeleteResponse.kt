package com.danielceinos.imgurcodetest.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Daniel S on 09/06/2018.
 */
data class ImageDeleteResponse(

    @SerializedName("data")
    @Expose
    val data: Boolean,
    @SerializedName("success")
    @Expose
    val success: Boolean,
    @SerializedName("status")
    @Expose
    val status: Int)