package com.danielceinos.imgurcodetest.presentation.gallery

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.danielceinos.imgurcodetest.common.ImageUtils
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
      imageRepository.uploadImage(
          ImageUploadRequest(scaleImageToBase64(imagePath), name, title, description))
          .observeOn(AndroidSchedulers.mainThread())
          .subscribeOn(Schedulers.io())
          .subscribe({ response ->
            loadImages()
            Log.i("Tag", "Image upload success")
            //TODO handle response error code
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