package com.danielceinos.imgurcodetest.data

import com.danielceinos.imgurcodetest.data.request.ImageUploadRequest
import com.danielceinos.imgurcodetest.data.request.RefreshTokenRequest
import com.danielceinos.imgurcodetest.data.request.TokenRequest
import com.danielceinos.imgurcodetest.data.response.ImageDeleteResponse
import com.danielceinos.imgurcodetest.data.response.ImageResponse
import com.danielceinos.imgurcodetest.data.response.ImagesResponse
import com.danielceinos.imgurcodetest.data.response.OauthToken
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*

/**
 * Created by Daniel S on 08/06/2018.
 */
interface ImgurService {

  @POST("oauth2/token")
  fun getToken(@Body tokenRequest: TokenRequest): Single<Response<OauthToken>>

  @POST("oauth2/token")
  fun refreshToken(@Body refreshTokenRequest: RefreshTokenRequest): Single<Response<OauthToken>>

  @GET("3/account/{username}/images/{page}")
  fun getImages(@Path(value = "username") username: String,
                @Path(value = "page") page: Int): Single<Response<ImagesResponse>>

  @POST("3/image")
  fun uploadImage(@Body imageUploadRequest: ImageUploadRequest): Single<Response<ImageResponse>>

  @DELETE("3/image/{imageHash}")
  fun deleteImage(@Path(value = "imageHash") imageHash: String): Single<Response<ImageDeleteResponse>>
}