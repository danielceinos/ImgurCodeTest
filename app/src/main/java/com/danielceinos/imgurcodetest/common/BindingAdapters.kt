package com.danielceinos.imgurcodetest.common

import android.databinding.BindingAdapter
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * Created by Daniel S on 09/06/2018.
 */
@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String?) {
  if (url == null || url == "") {
    imageView.setImageDrawable(null)
    imageView.visibility = View.GONE
  } else {
    imageView.visibility = View.VISIBLE
    Glide.with(imageView)
        .load(url)
        .into(imageView)
  }
}