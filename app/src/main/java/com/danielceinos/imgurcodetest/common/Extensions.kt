package com.danielceinos.imgurcodetest.common

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.widget.EditText

/**
 * Created by Daniel S on 08/06/2018.
 */


fun EditText.text(): String = text.toString()


fun Bitmap.scale(MAX_IMAGE_SIZE: Float): Bitmap {
  val oldWidth = this.width.toFloat()
  val oldHeight = this.height.toFloat()
  val ratio = oldHeight / oldWidth
  val maxPixels = MAX_IMAGE_SIZE / 4 //In ARGB_8888 each pixel is stored on 4 bytes

  val newWidth = Math.sqrt((maxPixels / ratio).toDouble()).toInt()
  val newHeight = (newWidth * ratio).toInt()

  val newbitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888)
  val canvas = Canvas(newbitmap)
  val matrix = Matrix()
  matrix.setScale(newWidth.toFloat() / oldWidth, newHeight.toFloat() / oldHeight)
  canvas.drawBitmap(this, matrix, Paint())

  return if (newWidth * newHeight < oldWidth * oldHeight) {
    newbitmap
  } else {
    this
  }
}
