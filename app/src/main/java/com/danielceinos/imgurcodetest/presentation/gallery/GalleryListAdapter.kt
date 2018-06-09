package com.danielceinos.imgurcodetest.presentation.gallery

import android.databinding.DataBindingUtil
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.danielceinos.imgurcodetest.R
import com.danielceinos.imgurcodetest.data.response.ImgurImage
import com.danielceinos.imgurcodetest.databinding.ItemGalleryBinding

/**
 * Created by Daniel S on 09/06/2018.
 */
class GalleryListAdapter : ListAdapter<ImgurImage, GalleryListAdapter.ItemViewHolder>(ListImageDiffCallback()) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    val binding = DataBindingUtil.inflate<ItemGalleryBinding>(inflater, R.layout.item_gallery, parent, false)
    return ItemViewHolder(binding)
  }

  override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
    holder.bind(getItem(position))
  }

  class ItemViewHolder(private val itemGalleryBinding: ItemGalleryBinding) : RecyclerView.ViewHolder(itemGalleryBinding.root) {

    fun bind(item: ImgurImage) {
      itemGalleryBinding.imageUrl = item.link
    }
  }
}