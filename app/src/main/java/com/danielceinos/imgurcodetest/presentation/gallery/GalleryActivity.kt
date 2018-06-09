package com.danielceinos.imgurcodetest.presentation.gallery

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.danielceinos.imgurcodetest.R
import com.danielceinos.imgurcodetest.data.response.ImgurImage
import com.danielceinos.imgurcodetest.databinding.ActivityGalleryBinding
import com.danielceinos.imgurcodetest.databinding.ContentGalleryBinding

import kotlinx.android.synthetic.main.activity_gallery.*
import kotlinx.android.synthetic.main.content_gallery.*

class GalleryActivity : AppCompatActivity() {


  private lateinit var mGalleryBinding: ActivityGalleryBinding
  private lateinit var mGalleryListAdapter: GalleryListAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    mGalleryBinding = DataBindingUtil.setContentView(this, R.layout.activity_gallery)
    setSupportActionBar(toolbar)

    val layoutManager = LinearLayoutManager(this)
    mGalleryListAdapter = GalleryListAdapter()

    rv_gallery.adapter = mGalleryListAdapter
    rv_gallery.layoutManager = layoutManager

  }
}
