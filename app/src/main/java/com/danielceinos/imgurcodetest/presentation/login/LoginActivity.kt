package com.danielceinos.imgurcodetest.presentation.login

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.danielceinos.imgurcodetest.R
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {

  @Inject
  lateinit var mViewModelFactory: ViewModelProvider.Factory

  private lateinit var mViewModel: LoginViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)

    mViewModel = ViewModelProviders.of(this, mViewModelFactory)[LoginViewModel::class.java]

    mViewModel.login("f25705520416faa", "2b1d60f0044e4c9f9bc061b65681612bcff5fd3e" )
  }
}
