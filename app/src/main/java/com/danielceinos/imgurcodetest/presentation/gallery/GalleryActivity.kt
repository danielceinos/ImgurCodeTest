package com.danielceinos.imgurcodetest.presentation.gallery

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import com.danielceinos.imgurcodetest.R
import com.danielceinos.imgurcodetest.common.scale
import com.danielceinos.imgurcodetest.databinding.ActivityGalleryBinding
import com.danielceinos.imgurcodetest.di.ViewModelFactory
import kotlinx.android.synthetic.main.activity_gallery.*
import kotlinx.android.synthetic.main.content_gallery.*
import org.jetbrains.anko.doAsync
import java.io.ByteArrayOutputStream
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

    mViewModel.loadImages()
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
      showConfirmationDialog()
    }
  }

  override fun onClick(upload: Boolean) {
    if (upload) {
      doAsync {
        var bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath)
        bitmap = bitmap.scale((1 * Math.pow(10.0, 6.0)).toFloat())
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        val encoded = android.util.Base64.encodeToString(byteArray, android.util.Base64.DEFAULT)
        Log.i("GalleryActivity", encoded)
        mViewModel.uploadImage(encoded, "", "", "")
      }
    }
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
    var bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath)
    bitmap = bitmap.scale((1 * Math.pow(10.0, 6.0)).toFloat())
    ImageUploadDialog(this, this, bitmap).show()
  }
}
