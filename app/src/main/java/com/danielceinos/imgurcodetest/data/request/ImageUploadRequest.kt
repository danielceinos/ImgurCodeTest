package com.danielceinos.imgurcodetest.data.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Daniel S on 09/06/2018.
 */
data class ImageUploadRequest(
    @SerializedName("image")
    @Expose
    val image: String,

    @SerializedName("name")
    @Expose
    val name: String,

    @SerializedName("title")
    @Expose
    val title: String,

    @SerializedName("description")
    @Expose
    val description: String
)