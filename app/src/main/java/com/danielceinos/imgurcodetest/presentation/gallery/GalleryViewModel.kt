package com.danielceinos.imgurcodetest.presentation.gallery

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.danielceinos.imgurcodetest.common.ImageUtils.ImageUtils.scaleImageToBase64
import com.danielceinos.imgurcodetest.data.ImageRepository
import com.danielceinos.imgurcodetest.data.request.ImageUploadRequest
import com.danielceinos.imgurcodetest.data.response.ImgurImage
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.doAsync
import javax.inject.Inject

/**
 * Created by Daniel S on 09/06/2018.
 */
class GalleryViewModel @Inject constructor(private val imageRepository: ImageRepository) : ViewModel() {

  val mGalleryViewState: MutableLiveData<GalleryViewState> = MutableLiveData()

  fun loadImages() {
    mGalleryViewState.postValue(mGalleryViewState.value?.copy(loading = true))
    imageRepository.getImagesForCurrentUser(0)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe({ response ->
          if (response.isSuccessful) {
            mGalleryViewState.postValue(GalleryViewState(false, true, response.body()?.data, null))
          } else {
            mGalleryViewState.postValue(mGalleryViewState.value?.copy(loading = false, success = false, msg = "Error al obtener las imagenes"))
          }
        }, { error ->
          Log.e("GalleryViewModel", error.localizedMessage)
        })
  }

  fun uploadImage(imagePath: String, name: String, title: String, description: String) {
    mGalleryViewState.postValue(GalleryViewState(true, false, mGalleryViewState.value?.images, "Subiendo imagen... "))
    doAsync {
      imageRepository.uploadImage(
          ImageUploadRequest(scaleImageToBase64(imagePath), name, title, description))
          .observeOn(AndroidSchedulers.mainThread())
          .subscribeOn(Schedulers.io())
          .subscribe({ response ->
            if (response.isSuccessful) {
              Log.i("GalleryViewModel", "Image upload success")
              val images = mutableListOf<ImgurImage>()
              response.body()?.data?.let { images.add(it) }
              mGalleryViewState.value?.images?.let { images.addAll(it) }
              mGalleryViewState.postValue(GalleryViewState(false, true, images, null))
            } else {
              Log.e("GalleryViewModel", "Image upload error: ${response.message()}")
              mGalleryViewState.postValue(mGalleryViewState.value?.copy(loading = false, success = false, msg = "Error al subir la imagen"))
            }
          }, { error ->
            Log.e("GalleryViewModel", error.localizedMessage)
          })
    }
  }

  fun deleteImage(imgurImage: ImgurImage) {
    mGalleryViewState.postValue(mGalleryViewState.value?.copy(loading = true, success = false, msg = "Borrando imagen..."))
    imageRepository.deleteImage(imgurImage)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe({ response ->
          if (response.isSuccessful) {
            Log.i("GalleryViewModel", "Image deleted success")
            val images =  mGalleryViewState.value?.images?.toMutableList()
            images?.remove(imgurImage)
            mGalleryViewState.postValue(GalleryViewState(false, true, images, null))
          } else {
            Log.e("GalleryViewModel", "Image deleted error: ${response.message()}")
            mGalleryViewState.postValue(mGalleryViewState.value?.copy(loading = false, success = false, msg = "Error al borrar la imagen"))
          }
        }, { error ->
          Log.e("GalleryViewModel", error.localizedMessage)
        })
  }

  data class GalleryViewState(val loading: Boolean,
                              val success: Boolean,
                              val images: List<ImgurImage>?,
                              val msg: String?)
}