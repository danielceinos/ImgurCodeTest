package com.danielceinos.imgurcodetest.presentation.gallery

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.danielceinos.imgurcodetest.data.response.ImgurImage
import com.danielceinos.imgurcodetest.databinding.ItemGalleryBinding

/**
 * Created by Daniel S on 09/06/2018.
 */
class GalleryListAdapter : ListAdapter<ImgurImage, GalleryListAdapter.ItemViewHolder>(ListImageDiffCallback()) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  class ItemViewHolder(private val itemGalleryBinding: ItemGalleryBinding) : RecyclerView.ViewHolder(itemGalleryBinding.root) {

    fun bind(item: ImgurImage) {

    }
  }
}