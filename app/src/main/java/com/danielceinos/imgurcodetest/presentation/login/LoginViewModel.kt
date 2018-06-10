package com.danielceinos.imgurcodetest.presentation.login

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.danielceinos.imgurcodetest.data.AuthRepository
import com.danielceinos.imgurcodetest.data.request.RefreshTokenRequest
import com.danielceinos.imgurcodetest.data.request.TokenRequest
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Daniel S on 08/06/2018.
 */
class LoginViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

  var mLoginViewState: MutableLiveData<LoginViewState> = MutableLiveData()

  init {
    authRepository.getRefreshToken()?.let {
      authRepository.doRefreshToken(RefreshTokenRequest(it))
          .observeOn(AndroidSchedulers.mainThread())
          .subscribeOn(Schedulers.io())
          .subscribe({ response ->
            Log.d("LoginViewModel", "${response.isSuccessful}")
            if (response.isSuccessful) {
              mLoginViewState.postValue(LoginViewState(true, "EXITO!! :)"))
            }
          }, { error ->
            Log.e("LoginViewModel", error.localizedMessage)
          })
    }
  }

  fun login(code: String) {
    authRepository.doAuth(TokenRequest(code))
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe({ response ->
          Log.d("LoginViewModel", "${response.isSuccessful}")
          if (response.isSuccessful) {
            mLoginViewState.postValue(LoginViewState(true, "EXITO!! :)"))
          } else {
            mLoginViewState.postValue(LoginViewState(false, response.message()))
          }
        }, { error ->
          Log.e("LoginViewModel", error.localizedMessage)
        })
  }

  data class LoginViewState(val success: Boolean, val msg: String)

}