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
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.danielceinos.imgurcodetest.R
import com.danielceinos.imgurcodetest.common.scale
import com.danielceinos.imgurcodetest.data.response.ImgurImage
import com.danielceinos.imgurcodetest.databinding.ActivityGalleryBinding
import com.danielceinos.imgurcodetest.di.ViewModelFactory
import com.danielceinos.imgurcodetest.presentation.gallery.GalleryViewModel.GalleryViewState
import kotlinx.android.synthetic.main.activity_gallery.*
import kotlinx.android.synthetic.main.content_gallery.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class GalleryActivity : AppCompatActivity(), ImageUploadDialog.DialogClickListener, GalleryListAdapter.OnImageLongClickListener {

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
    mGalleryListAdapter = GalleryListAdapter(this)

    rv_gallery.adapter = mGalleryListAdapter
    rv_gallery.layoutManager = layoutManager

    mViewModel.mGalleryViewState.observe(this, Observer {
      if (it != null)
        renderViewState(it)
    })

    mViewModel.loadImages()
  }

  private fun renderViewState(galleryViewState: GalleryViewState) {
    galleryViewState.images?.let { mGalleryListAdapter.submitList(it) }
    if (galleryViewState.loading) {
      loading_progress_bar?.visibility = View.VISIBLE
    } else {
      loading_progress_bar?.visibility = View.GONE
    }
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
      showUploadConfirmationDialog()
    }
  }

  override fun onClick(upload: Boolean) {
    if (upload) {
      mCurrentPhotoPath?.let { mViewModel.uploadImage(it, "", "", "") }
    }
  }

  override fun onLongClick(imgurImage: ImgurImage) {
    showDeleteConfirmationDialog(imgurImage)
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

  private fun showUploadConfirmationDialog() {
    var bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath)
    bitmap = bitmap.scale((1 * Math.pow(10.0, 6.0)).toFloat())
    ImageUploadDialog(this, this, bitmap).show()
  }

  private fun showDeleteConfirmationDialog(imgurImage: ImgurImage) {
    val alertDialog = AlertDialog.Builder(this).create()
    alertDialog.setTitle("Borar imagen")
    alertDialog.setMessage("¿Estás seguro que quieres borrar esta imagen?")
    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Si",
        { dialog, _ ->
          run {
            mViewModel.deleteImage(imgurImage)
            dialog.dismiss()
          }
        })
    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
        { dialog, _ -> dialog.dismiss() })
    alertDialog.show()
  }
}