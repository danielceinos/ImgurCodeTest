package com.danielceinos.imgurcodetest.presentation.login

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.danielceinos.imgurcodetest.data.AuthRepository
import com.danielceinos.imgurcodetest.data.request.TokenRequest
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Daniel S on 08/06/2018.
 */
class LoginViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

  var mLoginViewState: MutableLiveData<LoginViewState> = MutableLiveData()

  fun login(password: String) {
    authRepository.doAuth(TokenRequest("d94ee637597774f",
        "b4b4ab0dd8f3aadb7275376e929559338618b3f8",
        "authorization_code",
        password))
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe({ response ->
          Log.d("LoginViewModel", "${response.isSuccessful}")
          if (response.isSuccessful) {
            if (response.code() == 200) {
              mLoginViewState.postValue(LoginViewState(true, "EXITO!! :)"))
            } else {
              mLoginViewState.postValue(LoginViewState(false, response.message()))
            }
          } else {
            mLoginViewState.postValue(LoginViewState(false, response.message()))
          }
        }, { error ->
          Log.e("Tag", error.localizedMessage)
        })
  }

  data class LoginViewState(val success: Boolean, val msg: String)

}