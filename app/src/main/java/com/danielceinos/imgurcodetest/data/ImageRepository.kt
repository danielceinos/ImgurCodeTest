package com.danielceinos.imgurcodetest.data

import com.danielceinos.imgurcodetest.data.request.ImageUploadRequest
import com.danielceinos.imgurcodetest.data.response.ImgurImage
import javax.inject.Inject

/**
 * Created by Daniel S on 09/06/2018.
 */
class ImageRepository @Inject constructor(private val imgurService: ImgurService,
                                          private val sharedPreferencesService: SharedPreferencesService) {

  fun getImagesForCurrentUser(page: Int) =
      imgurService.getImages(
          sharedPreferencesService.getAccountUsername() ?: "",
          page)

  fun uploadImage(imageUploadRequest: ImageUploadRequest) =
      imgurService.uploadImage(imageUploadRequest)

  fun deleteImage(image: ImgurImage) =
      imgurService.deleteImage(image.deletehash ?: "")
}
