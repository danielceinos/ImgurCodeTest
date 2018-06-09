package com.danielceinos.imgurcodetest.presentation.gallery

import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.Window
import com.danielceinos.imgurcodetest.R
import kotlinx.android.synthetic.main.dialog_upload.*


/**
 * Created by Daniel S on 09/06/2018.
 */
class ImageUploadDialog(context: Context?,
                        private val dialogClickListener: DialogClickListener,
                        private val bitmap: Bitmap) : Dialog(context) {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    requestWindowFeature(Window.FEATURE_NO_TITLE)
    setContentView(R.layout.dialog_upload)
    iv_image.setImageBitmap(bitmap)
    b_cancel.setOnClickListener { dialogClickListener.onClick(false) }
    b_upload.setOnClickListener { dialogClickListener.onClick(true) }
  }

  interface DialogClickListener {
    fun onClick(upload: Boolean)
  }
}