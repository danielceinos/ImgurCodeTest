package com.danielceinos.imgurcodetest.data

import javax.inject.Inject

/**
 * Created by Daniel S on 09/06/2018.
 */
class ImageRepository @Inject constructor(private val imgurService: ImgurService,
                                          private val sharedPreferencesService: SharedPreferencesService) {

  fun getImagesForCurrentUser(page: Int) =
      imgurService.getImages(
          "Bearer ${sharedPreferencesService.getPref("token")}",
          sharedPreferencesService.getPref("account_username"),
          page)
}