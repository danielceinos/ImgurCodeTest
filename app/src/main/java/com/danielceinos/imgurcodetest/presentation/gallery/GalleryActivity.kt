package com.danielceinos.imgurcodetest.presentation.gallery

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import com.danielceinos.imgurcodetest.R
import com.danielceinos.imgurcodetest.databinding.ActivityGalleryBinding
import com.danielceinos.imgurcodetest.di.ViewModelFactory
import kotlinx.android.synthetic.main.activity_gallery.*
import kotlinx.android.synthetic.main.content_gallery.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class GalleryActivity : AppCompatActivity(), ImageUploadDialog.DialogClickListener {

  @Inject
  lateinit var mViewModelFactory: ViewModelFactory

  private lateinit var mGalleryBinding: ActivityGalleryBinding
  private lateinit var mGalleryListAdapter: GalleryListAdapter
  private lateinit var mViewModel: GalleryViewModel
  private var mCurrentPhotoPath: String? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    mGalleryBinding = DataBindingUtil.setContentView(this, R.layout.activity_gallery)
    setSupportActionBar(toolbar)

    mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(GalleryViewModel::class.java)

    mGalleryBinding.fabUpload.setOnClickListener {
      launchCamera()
    }
    val layoutManager = GridLayoutManager(this, 3)
    mGalleryListAdapter = GalleryListAdapter()

    rv_gallery.adapter = mGalleryListAdapter
    rv_gallery.layoutManager = layoutManager

    mViewModel.mGalleryViewState.observe(this, Observer {
      mGalleryListAdapter.submitList(it?.images)
    })

    mViewModel.load()
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
      showConfirmationDialog()
    }
  }

  override fun onClick(upload: Boolean) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  private fun launchCamera() {
    val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    if (takePictureIntent.resolveActivity(packageManager) != null) {
      var photoFile: File? = null
      try {
        photoFile = createImageFile()
      } catch (ex: IOException) {
      }
      if (photoFile != null) {
        val photoURI = FileProvider.getUriForFile(
            this,
            "com.danielceinos.imgurcodetest.fileprovider",
            photoFile)
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
        startActivityForResult(takePictureIntent, 1)
      }
    }
  }

  private fun createImageFile(): File {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    val image = File.createTempFile(imageFileName, ".jpg", storageDir)

    mCurrentPhotoPath = image.absolutePath
    return image
  }

  private fun showConfirmationDialog() {
    val bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath)
    ImageUploadDialog(this, this, bitmap).show()
  }
}
