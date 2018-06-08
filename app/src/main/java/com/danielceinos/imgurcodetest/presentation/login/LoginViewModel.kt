package com.danielceinos.imgurcodetest.presentation.login

import android.arch.lifecycle.ViewModel
import android.util.Log
import com.danielceinos.imgurcodetest.data.ImgurService
import com.danielceinos.imgurcodetest.data.request.TokenRequest
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Daniel S on 08/06/2018.
 */
class LoginViewModel @Inject constructor(private val imgurService: ImgurService) : ViewModel() {

  fun login(username: String, password: String) {
    imgurService.getToken(TokenRequest(username,
        "05608981340475504adf51b8c37b8b2c18e025c2",
        "authorization_code",
        password))
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe({ response ->
          Log.d("LoginViewModel", "${response.isSuccessful}")
        }, { error ->
          Log.e("Tag", error.localizedMessage)
        })
  }
}