package com.danielceinos.imgurcodetest.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


/**
 * Created by Daniel S on 09/06/2018.
 */
data class ImgurImage(
    @SerializedName("id")
    @Expose
    val id: String,
    @SerializedName("title")
    @Expose
    val title: Any? = null,
    @SerializedName("description")
    @Expose
    val description: Any? = null,
    @SerializedName("datetime")
    @Expose
    val datetime: Int? = null,
    @SerializedName("type")
    @Expose
    val type: String? = null,
    @SerializedName("animated")
    @Expose
    val animated: Boolean? = null,
    @SerializedName("width")
    @Expose
    val width: Int? = null,
    @SerializedName("height")
    @Expose
    val height: Int? = null,
    @SerializedName("size")
    @Expose
    val size: Int? = null,
    @SerializedName("views")
    @Expose
    val views: Int? = null,
    @SerializedName("bandwidth")
    @Expose
    val bandwidth: Int? = null,
    @SerializedName("vote")
    @Expose
    val vote: Any? = null,
    @SerializedName("favorite")
    @Expose
    val favorite: Boolean? = null,
    @SerializedName("nsfw")
    @Expose
    val nsfw: Any? = null,
    @SerializedName("section")
    @Expose
    val section: Any? = null,
    @SerializedName("account_url")
    @Expose
    val accountUrl: String? = null,
    @SerializedName("account_id")
    @Expose
    val accountId: Int? = null,
    @SerializedName("is_ad")
    @Expose
    val isAd: Boolean? = null,
    @SerializedName("in_most_viral")
    @Expose
    val inMostViral: Boolean? = null,
    @SerializedName("has_sound")
    @Expose
    val hasSound: Boolean? = null,
    @SerializedName("tags")
    @Expose
    val tags: List<Any>? = null,
    @SerializedName("ad_type")
    @Expose
    val adType: Int? = null,
    @SerializedName("ad_url")
    @Expose
    val adUrl: String? = null,
    @SerializedName("in_gallery")
    @Expose
    val inGallery: Boolean? = null,
    @SerializedName("deletehash")
    @Expose
    val deletehash: String? = null,
    @SerializedName("name")
    @Expose
    val name: String? = null,
    @SerializedName("link")
    @Expose
    val link: String)