package com.danielceinos.imgurcodetest.presentation.gallery

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.danielceinos.imgurcodetest.data.ImageRepository
import com.danielceinos.imgurcodetest.data.request.ImageUploadRequest
import com.danielceinos.imgurcodetest.data.response.ImgurImage
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Daniel S on 09/06/2018.
 */
class GalleryViewModel @Inject constructor(private val imageRepository: ImageRepository) : ViewModel() {

  val mGalleryViewState: MutableLiveData<GalleryViewState> = MutableLiveData()

  fun loadImages() {
    imageRepository.getImagesForCurrentUser(0)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe({ response ->
          if (response.isSuccessful) {
            mGalleryViewState.postValue(GalleryViewState(true, response.body()?.data, null))
          } else {
            mGalleryViewState.postValue(GalleryViewState(false, null, null))
          }
        }, { error ->
          Log.e("Tag", error.localizedMessage)
        })
  }

  fun uploadImage(image: String, name: String, title: String, description: String) {
    imageRepository.uploadImage(ImageUploadRequest(image, name, title, description))
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

  data class GalleryViewState(val success: Boolean, val images: List<ImgurImage>?, val msg: String?)
}