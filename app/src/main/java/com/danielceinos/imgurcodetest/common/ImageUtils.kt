package com.danielceinos.imgurcodetest.common

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream

/**
 * Created by Daniel S on 09/06/2018.
 */
class ImageUtils {
  companion object ImageUtils {
    fun scaleImageToBase64(imagePath: String): String {
      var bitmap = BitmapFactory.decodeFile(imagePath)
      bitmap = bitmap.scale((1 * Math.pow(10.0, 6.0)).toFloat())
      val byteArrayOutputStream = ByteArrayOutputStream()
      bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
      val byteArray = byteArrayOutputStream.toByteArray()

      return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }
  }

}