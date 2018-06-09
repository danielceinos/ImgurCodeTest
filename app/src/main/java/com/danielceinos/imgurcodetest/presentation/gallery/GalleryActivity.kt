package com.danielceinos.imgurcodetest.presentation.gallery

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import com.danielceinos.imgurcodetest.R
import com.danielceinos.imgurcodetest.databinding.ActivityGalleryBinding
import com.danielceinos.imgurcodetest.di.ViewModelFactory
import kotlinx.android.synthetic.main.activity_gallery.*
import kotlinx.android.synthetic.main.content_gallery.*
import javax.inject.Inject

class GalleryActivity : AppCompatActivity() {

  @Inject
  lateinit var mViewModelFactory: ViewModelFactory

  private lateinit var mGalleryBinding: ActivityGalleryBinding
  private lateinit var mGalleryListAdapter: GalleryListAdapter
  private lateinit var mViewModel: GalleryViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    mGalleryBinding = DataBindingUtil.setContentView(this, R.layout.activity_gallery)
    setSupportActionBar(toolbar)

    mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(GalleryViewModel::class.java)

    val layoutManager = GridLayoutManager(this, 3)
    mGalleryListAdapter = GalleryListAdapter()

    rv_gallery.adapter = mGalleryListAdapter
    rv_gallery.layoutManager = layoutManager

    mViewModel.mGalleryViewState.observe(this, Observer {
      mGalleryListAdapter.submitList(it?.images)
    })

    mViewModel.load()
  }
}
