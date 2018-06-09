package com.danielceinos.imgurcodetest.presentation.gallery

import android.support.v7.util.DiffUtil
import com.danielceinos.imgurcodetest.data.response.ImgurImage

/**
 * Created by Daniel S on 09/06/2018.
 */
class ListImageDiffCallback : DiffUtil.ItemCallback<ImgurImage>() {

  override fun areItemsTheSame(oldImage: ImgurImage?, newImage: ImgurImage?): Boolean {
    return oldImage?.id == newImage?.id
  }

  override fun areContentsTheSame(oldImage: ImgurImage?, newImage: ImgurImage?): Boolean {
    return oldImage == newImage
  }
}