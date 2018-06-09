package com.danielceinos.imgurcodetest.presentation.gallery

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.danielceinos.imgurcodetest.common.scale
import com.danielceinos.imgurcodetest.data.ImageRepository
import com.danielceinos.imgurcodetest.data.request.ImageUploadRequest
import com.danielceinos.imgurcodetest.data.response.ImgurImage
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.doAsync
import java.io.ByteArrayOutputStream
import javax.inject.Inject

/**
 * Created by Daniel S on 09/06/2018.
 */
class GalleryViewModel @Inject constructor(private val imageRepository: ImageRepository) : ViewModel() {

  val mGalleryViewState: MutableLiveData<GalleryViewState> = MutableLiveData()

  fun loadImages() {
    mGalleryViewState.postValue(GalleryViewState(true, false, mGalleryViewState.value?.images, null))
    imageRepository.getImagesForCurrentUser(0)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe({ response ->
          if (response.isSuccessful) {
            mGalleryViewState.postValue(GalleryViewState(false, true, response.body()?.data, null))
          } else {
            mGalleryViewState.postValue(GalleryViewState(false, false, null, null))
          }
        }, { error ->
          Log.e("Tag", error.localizedMessage)
        })
  }

  fun uploadImage(imagePath: String, name: String, title: String, description: String) {
    mGalleryViewState.postValue(GalleryViewState(true, false, mGalleryViewState.value?.images, null))
    doAsync {
      var bitmap = BitmapFactory.decodeFile(imagePath)
      bitmap = bitmap.scale((1 * Math.pow(10.0, 6.0)).toFloat())
      val byteArrayOutputStream = ByteArrayOutputStream()
      bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
      val byteArray = byteArrayOutputStream.toByteArray()
      val encoded = android.util.Base64.encodeToString(byteArray, android.util.Base64.DEFAULT)

      imageRepository.uploadImage(ImageUploadRequest(encoded, name, title, description))
          .observeOn(AndroidSchedulers.mainThread())
          .subscribeOn(Schedulers.io())
          .subscribe({ response ->
            loadImages()
            Log.i("Tag", "Image upload success")
            //TODO handle response error code
            //TODO ocultar dialogo
          }, { error ->
            Log.e("Tag", error.localizedMessage)
          })
    }
  }

  fun deleteImage(imgurImage: ImgurImage) {
    mGalleryViewState.postValue(GalleryViewState(true, false, mGalleryViewState.value?.images, null))
    imageRepository.deleteImage(imgurImage)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe({ response ->
          loadImages()
          Log.i("Tag", "Image deleted success")
          //TODO handle response error code
        }, { error ->
          Log.e("Tag", error.localizedMessage)
        })
  }

  data class GalleryViewState(val loading: Boolean,
                              val success: Boolean,
                              val images: List<ImgurImage>?,
                              val msg: String?)
}